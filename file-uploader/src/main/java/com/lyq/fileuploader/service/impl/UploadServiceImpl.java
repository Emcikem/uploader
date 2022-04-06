package com.lyq.fileuploader.service.impl;

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

    @Resource(name = "${fileType}")
    private IFileService fileService;


    @Override
    public FileChunkResultDTO checkChunkExist(FileChunkDTO chunkDTO) {
        fileService.deleteDirectory("1");
        // 1. 检查redis中是否存在，并且所有分片已经上传完成
        Set<Integer> uploaded = (Set<Integer>) redisTemplate.opsForHash().get(chunkDTO.getIdentifier(), "uploaded");
        if (uploaded != null && uploaded.size() == chunkDTO.getTotalChunks()) {
            return new FileChunkResultDTO(true);
        }
        Boolean isUploaded = redisTemplate.opsForHash().hasKey(chunkDTO.getIdentifier(), "isUploaded");
        if (isUploaded) {
            return new FileChunkResultDTO(true);
        }

        // 2. mysql中存在这个数据了，直接秒传


        // 3. 创建文件夹用于做分片
        String chunkFileFolderPath = getFileFolderPath(chunkDTO.getIdentifier());
        boolean mkdirs = fileService.mkdirs(chunkFileFolderPath);
        log.info("准备工作,创建文件夹,fileFolderPath:{},mkdirs:{}", chunkFileFolderPath, mkdirs);

        return new FileChunkResultDTO(false, uploaded);
    }

    @Override
    public void uploadChunk(FileChunkDTO chunkDTO) {
        String chunkFileFolderPath = getFileFolderPath(chunkDTO.getIdentifier());

        try {
            InputStream inputStream = chunkDTO.getFile().getInputStream();
            String filePath = chunkFileFolderPath + chunkDTO.getChunkNumber();
            fileService.copyFile(inputStream, filePath);

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
            fileService.deleteDirectory(getFileFolderPath(identifier));
            // TODO删除redis的数据
            return true;
        }
        return false;
    }

    private boolean mergeChunks(String identifier, String fileName, Integer totalChunks) {
        String chunkFileFolderPath = getFileFolderPath(identifier);
        String filePath = getFilePath(fileName);
        // 检查分片是否都存在，然后合并分片
        return fileService.mergeChunks(chunkFileFolderPath, filePath, totalChunks);
    }


    /**
     * 删除分片缓存数据
     */
    private void deleteRedis(FileChunkDTO chunkDTO) {
        // 删除分片的缓存
        redisTemplate.opsForHash().delete(chunkDTO.getIdentifier(), "uploaded");
        // 设置为终态
        redisTemplate.opsForHash().put(chunkDTO.getIdentifier(), "isUploaded", "true");
    }

    /**
     * 分片写入redis
     */
    private synchronized void saveToRedis(FileChunkDTO chunkDTO) {
        Set<Integer> uploaded = (Set<Integer>) redisTemplate.opsForHash().get(chunkDTO.getIdentifier(), "uploaded");
        if (uploaded == null) {
            uploaded = new HashSet<>(Collections.singletonList(chunkDTO.getChunkNumber()));
            HashMap<String, Object> objectHashMap = new HashMap<>();
            objectHashMap.put("uploaded", uploaded);
            objectHashMap.put("totalChunks", chunkDTO.getTotalChunks());
            objectHashMap.put("totalSize", chunkDTO.getTotalSize());
            objectHashMap.put("name", chunkDTO.getFilename());
            redisTemplate.opsForHash().putAll(chunkDTO.getIdentifier(), objectHashMap);
        } else {
            uploaded.add(chunkDTO.getChunkNumber());
            redisTemplate.opsForHash().put(chunkDTO.getIdentifier(), "uploaded", uploaded);
        }
    }

    /**
     * 得到文件村粗的绝对路径
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
}
