package com.lyq.file.service;

import com.lyq.file.dto.FileChunkDTO;
import com.lyq.file.dto.FileChunkResultDTO;

import java.io.FileNotFoundException;
import java.io.IOException;


public interface IUploadService {


    /**
     * 检查文件是否存在，如果存在则跳过该文件的上传，如果不存在，返回需要上传的分片集合
     */
    FileChunkResultDTO checkChunkExist(FileChunkDTO chunkDTO) throws FileNotFoundException;

    /**
     * 上传文件分片
     */
    void uploadChunk(FileChunkDTO chunkDTO) throws IOException;


    /**
     * 合并文件分片
     */
    boolean mergeChunk(String identifier,String fileName,Integer totalChunks, Long totalSize) throws FileNotFoundException;

}
