package com.lyq.fileuploader.controller;

import com.lyq.fileuploader.dto.FileChunkDTO;
import com.lyq.fileuploader.dto.FileChunkResultDTO;
import com.lyq.fileuploader.response.RestApiResponse;
import com.lyq.fileuploader.service.IUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("upload")
public class UploadController {

    @Autowired
    private IUploadService uploadService;

    /**
     * 检查分片是否存在
     */
    @GetMapping("chunk")
    public RestApiResponse<Object> checkChunkExist(FileChunkDTO chunkDTO) {
        FileChunkResultDTO fileChunkResultDTO = uploadService.checkChunkExist(chunkDTO);
        return RestApiResponse.success(fileChunkResultDTO);
    }

    /**
     * 上传文件分片
     */
    @PostMapping("/chunk")
    public RestApiResponse<Object> uploadChunk(FileChunkDTO chunkDTO) {
        uploadService.uploadChunk(chunkDTO);
        return RestApiResponse.success(chunkDTO.getIdentifier());
    }

    /**
     * 请求合并文件分片
     */
    @PostMapping("/merge")
    public RestApiResponse<Object> mergeChunks(@RequestBody FileChunkDTO chunkDTO) {
        boolean success = uploadService.mergeChunk(chunkDTO.getIdentifier(), chunkDTO.getFilename(), chunkDTO.getTotalChunks());
        return RestApiResponse.success(success);
    }
}
