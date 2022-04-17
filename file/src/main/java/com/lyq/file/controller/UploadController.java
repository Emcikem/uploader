package com.lyq.file.controller;

import com.lyq.file.dto.FileChunkDTO;
import com.lyq.file.dto.FileChunkResultDTO;
import com.lyq.file.response.RestApiResponse;
import com.lyq.file.service.IUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;
import java.io.IOException;

@RestController
@RequestMapping("upload")
public class UploadController {

    @Autowired
    private IUploadService uploadService;

    /**
     * 检查分片是否存在
     */
    @GetMapping("/chunk")
    public RestApiResponse<Object> checkChunkExist(FileChunkDTO chunkDTO) {
        try {
            FileChunkResultDTO fileChunkResultDTO = uploadService.checkChunkExist(chunkDTO);
            return RestApiResponse.success(fileChunkResultDTO);
        } catch (FileNotFoundException e) {
            return RestApiResponse.error(e.toString());
        }
    }

    /**
     * 上传文件分片
     */
    @PostMapping("/chunk")
    public RestApiResponse<Object> uploadChunk(FileChunkDTO chunkDTO) {
        try {
            uploadService.uploadChunk(chunkDTO);
            return RestApiResponse.success(chunkDTO.getIdentifier());
        } catch (IOException e) {
            return RestApiResponse.error(e.toString());
        }
    }

    /**
     * 请求合并文件分片
     */
    @PostMapping("/merge")
    public RestApiResponse<Object> mergeChunks(@RequestBody FileChunkDTO chunkDTO) {
        try {
            boolean success = uploadService.mergeChunk(
                    chunkDTO.getIdentifier(),
                    chunkDTO.getFilename(),
                    chunkDTO.getTotalChunks(),
                    chunkDTO.getTotalSize());
            return RestApiResponse.success(success);
        } catch (FileNotFoundException e) {
            return RestApiResponse.error(e.toString());
        }
    }
}
