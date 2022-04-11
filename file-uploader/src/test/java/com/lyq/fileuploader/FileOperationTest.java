package com.lyq.fileuploader;

import com.lyq.fileuploader.dto.FileChunkDTO;
import com.lyq.fileuploader.dto.FileChunkResultDTO;
import com.lyq.fileuploader.service.IUploadService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class FileOperationTest extends BaseTest{
    @Autowired
    private IUploadService uploadService;

    @Test
    public void checkChunkExist() {
        FileChunkDTO fileChunkDTO = new FileChunkDTO();
        fileChunkDTO.setIdentifier("249e5943588ed93dc0b48587439c721d");
        fileChunkDTO.setFilename("计算机科学技术学院(1).xlsx");
        fileChunkDTO.setCurrentChunkSize(10485760L);
        fileChunkDTO.setTotalChunks(3);
        fileChunkDTO.setChunkNumber(1);
        fileChunkDTO.setTotalSize(37817685L);
        fileChunkDTO.setChunkSize(10485760L);
        FileChunkResultDTO fileChunkResultDTO = uploadService.checkChunkExist(fileChunkDTO);
        System.out.println(fileChunkResultDTO.toString());
    }

    @Test
    public void delete() {
//        UploadServiceImpl.deleteDirectory("/Users/emcikem/Downloads/java-upload-master/file-uploader/src/test/java/com/lyq/fileuploader/myfile");
    }
}
