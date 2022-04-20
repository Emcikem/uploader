package com.lyq.file.service.impl;

import com.lyq.file.FileServiceStrategy;
import com.lyq.file.constant.RedisConst;
import com.lyq.file.dto.FileChunkDTO;
import com.lyq.file.dto.FileChunkResultDTO;
import com.lyq.file.dto.entity.FIlePO;
import com.lyq.file.repository.FilePoRepository;
import com.lyq.file.service.IFileService;
import com.lyq.file.service.IUploadService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import javax.annotation.Resource;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;


@Slf4j
@Service
public class UploadServiceImpl implements IUploadService {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Resource
    private FilePoRepository repository;

    @Resource
    private FileServiceStrategy strategy;


    @Override
    public FileChunkResultDTO checkChunkExist(FileChunkDTO chunkDTO) throws FileNotFoundException {

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


        // 3. 创建文件夹用于做分片，没有这个文件
        String chunkFileFolderPath = getFileFolderPath(chunkDTO.getIdentifier());
        log.info("chunkFileFolderPath: {}", chunkFileFolderPath);
        boolean mkdirs = getFileStrategy().mkdirs(chunkFileFolderPath);
        log.info("准备工作,创建文件夹,fileFolderPath:{},mkdirs:{}", chunkFileFolderPath, mkdirs);

        return new FileChunkResultDTO(false, uploaded);
    }


    @Override
    public void uploadChunk(FileChunkDTO chunkDTO) throws IOException {
        String chunkFileFolderPath = getFileFolderPath(chunkDTO.getIdentifier());

        InputStream inputStream = chunkDTO.getFile().getInputStream();
        String filePath = chunkFileFolderPath + chunkDTO.getChunkNumber();
        getFileStrategy().copyFile(inputStream, filePath);

        log.info("文件标识:{},chunkNumber:{}", chunkDTO.getIdentifier(), chunkDTO.getChunkNumber());

        saveToRedis(chunkDTO);
    }


    @Override
    public boolean mergeChunk(String identifier, String fileName, Integer totalChunks, Long totalSize) throws FileNotFoundException {
        if (mergeChunks(identifier, fileName, totalChunks)) {
            log.info("合并成功, identifier:{}", identifier);
            getFileStrategy().deleteDirectory(getFileFolderPath(identifier));
            // TODO删除redis的数据
            saveTOSQL(identifier, fileName, totalSize);
            return true;
        }
        return false;
    }

    private void saveTOSQL(String identifier, String fileName, Long totalSize) {
        FIlePO fIlePO = FIlePO.builder()
                .filePath(getFilePath(fileName))
                .updateTime(System.currentTimeMillis())
                .totalSize(totalSize)
                .identifier(identifier)
                .fileType(fileName.substring(fileName.lastIndexOf(".")))
                .createTime(System.currentTimeMillis())
                .filename(fileName)
                .deleted(0)
                .build();

        repository.save(fIlePO);
    }

    private boolean mergeChunks(String identifier, String fileName, Integer totalChunks) throws FileNotFoundException {
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
        String uploadFolder = getUploadFolder();
        String prePath = this.getClass().getClassLoader().getResource("").getPath();
//        System.out.printf("%s%s/%s%n", prePath, uploadFolder, filename);
        return String.format("%s%s/%s", prePath, uploadFolder, filename);
    }

    /**
     * 获取相对路径
     */
    private String getRelationPath(String filename) {
        return String.format("/%s/%s", getUploadFolder(), filename);
    }

    /**
     * 得到文件所属的目录(存放分片文件)
     */
    private String getFileFolderPath(String identifier) {
        String uploadFolder = getUploadFolder();
        String prePath = this.getClass().getClassLoader().getResource("").getPath();
//        System.out.printf("%s%s/chunk/%s/%n", prePath, uploadFolder, identifier);
        return String.format("%s%s/chunk/%s/", prePath, uploadFolder, identifier);
    }


    /**
     * 从redis的业务数据里获取存储策略，通过策略模式返回策略接口
     */
    private IFileService getFileStrategy () {
        String type = (String) redisTemplate.opsForHash().get(RedisConst.STRATEGY, RedisConst.STORETYPE);
        type = type == null ? "LOCAL" : type;
        return strategy.getStrategy(type);
    }

    private String getUploadFolder () {
        String path = (String) redisTemplate.opsForHash().get(RedisConst.STRATEGY, RedisConst.UPLOADERFOLDER);
        return path == null ? "file" : path;
    }
}
