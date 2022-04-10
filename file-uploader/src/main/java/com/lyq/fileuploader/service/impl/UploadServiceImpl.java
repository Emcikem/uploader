package com.lyq.fileuploader.service.impl;

import com.lyq.fileuploader.FileServiceStrategy;
import com.lyq.fileuploader.constant.RedisConst;
import com.lyq.fileuploader.dto.FileChunkDTO;
import com.lyq.fileuploader.dto.FileChunkResultDTO;
import com.lyq.fileuploader.service.IFileService;
import com.lyq.fileuploader.service.IUploadService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;


@Slf4j
@Service
public class UploadServiceImpl implements IUploadService {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Value("${uploadFolder}")
    private String uploadFolder;

    @Resource
    private FileServiceStrategy strategy;


    @Override
    public FileChunkResultDTO checkChunkExist(FileChunkDTO chunkDTO) {
        // 1. 检查redis中是否存在，并且所有分片已经上传完成
        @SuppressWarnings("unchecked")
        Set<Integer> uploaded = (Set<Integer>) redisTemplate.opsForHash()
                .get(chunkDTO.getIdentifier(), RedisConst.UPLOADED);

        if (uploaded != null && uploaded.size() == chunkDTO.getTotalChunks()) {
            return new FileChunkResultDTO(true);
        }
        Boolean isUploaded = redisTemplate.opsForHash().hasKey(chunkDTO.getIdentifier(), RedisConst.ISUPLOADED);
        if (isUploaded) {
            return new FileChunkResultDTO(true);
        }

        // 2. mysql中存在这个数据了，直接秒传


        // 3. 创建文件夹用于做分片
        String chunkFileFolderPath = getFileFolderPath(chunkDTO.getIdentifier());
        boolean mkdirs = getFileStrategy().mkdirs(chunkFileFolderPath);
        log.info("准备工作,创建文件夹,fileFolderPath:{},mkdirs:{}", chunkFileFolderPath, mkdirs);

        return new FileChunkResultDTO(false, uploaded);
    }

    @Override
    public void uploadChunk(FileChunkDTO chunkDTO) {
        String chunkFileFolderPath = getFileFolderPath(chunkDTO.getIdentifier());

        try {
            InputStream inputStream = chunkDTO.getFile().getInputStream();
            String filePath = chunkFileFolderPath + chunkDTO.getChunkNumber();
            getFileStrategy().copyFile(inputStream, filePath);

            log.info("文件标识:{},chunkNumber:{}", chunkDTO.getIdentifier(), chunkDTO.getChunkNumber());

            saveToRedis(chunkDTO);
        } catch (IOException e) {
            // TODO
            log.warn("分片创建失败");
        }
    }


    @Override
    public boolean mergeChunk(String identifier, String fileName, Integer totalChunks) {
        if (mergeChunks(identifier, fileName, totalChunks)) {
            log.info("合并成功, identifier:{}", identifier);
            getFileStrategy().deleteDirectory(getFileFolderPath(identifier));
            // TODO删除redis的数据
            return true;
        }
        return false;
    }

    private boolean mergeChunks(String identifier, String fileName, Integer totalChunks) {
        String chunkFileFolderPath = getFileFolderPath(identifier);
        String filePath = getFilePath(fileName);
        // 检查分片是否都存在，然后合并分片
        return getFileStrategy().mergeChunks(chunkFileFolderPath, filePath, totalChunks);
    }


    /**
     * 删除分片缓存数据
     */
    private void deleteRedis(FileChunkDTO chunkDTO) {
        // 删除分片的缓存
        redisTemplate.opsForHash().delete(chunkDTO.getIdentifier(), RedisConst.UPLOADED);
        // 设置为终态
        redisTemplate.opsForHash().put(chunkDTO.getIdentifier(), RedisConst.ISUPLOADED, "true");
    }

    /**
     * 分片写入redis
     */
    private synchronized void saveToRedis(FileChunkDTO chunkDTO) {
        @SuppressWarnings("unchecked")
        Set<Integer> uploaded = (Set<Integer>) redisTemplate.opsForHash()
                .get(chunkDTO.getIdentifier(), RedisConst.UPLOADED);

        if (uploaded == null) {
            uploaded = new HashSet<>(Collections.singletonList(chunkDTO.getChunkNumber()));
            HashMap<String, Object> objectHashMap = new HashMap<>();

            objectHashMap.put(RedisConst.UPLOADED, uploaded);
            objectHashMap.put(RedisConst.TOTALCHUNKS, chunkDTO.getTotalChunks());
            objectHashMap.put(RedisConst.TOTALSIZE, chunkDTO.getTotalSize());
            objectHashMap.put(RedisConst.NAME, chunkDTO.getFilename());
            redisTemplate.opsForHash().putAll(chunkDTO.getIdentifier(), objectHashMap);
        } else {
            uploaded.add(chunkDTO.getChunkNumber());
            redisTemplate.opsForHash().put(chunkDTO.getIdentifier(), RedisConst.UPLOADED, uploaded);
        }
    }

    /**
     * 得到文件存储的绝对路径
     */
    private String getFilePath(String filename) {
        return String.format("%s/%s", uploadFolder, filename);
    }


    /**
     * 得到文件所属的目录(存放分片文件)
     */
    private String getFileFolderPath(String identifier) {
        return String.format("%s/chunk/%s/", uploadFolder, identifier);
    }


    /**
     * 从redis的业务数据里获取存储策略，通过策略模式返回策略接口
     */
    private IFileService getFileStrategy () {
        String type = (String) redisTemplate.opsForHash().get(RedisConst.STRATEGY, RedisConst.STORE);
        return strategy.getStrategy(type);
    }
}
