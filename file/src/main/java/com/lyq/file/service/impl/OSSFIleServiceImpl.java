package com.lyq.file.service.impl;

import com.lyq.file.enume.FileStrategy;
import com.lyq.file.service.IFileService;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;

@Service
public class OSSFIleServiceImpl implements IFileService {

    @Override
    public void deleteDirectory(String folderPath) {
        System.out.println("22");
    }

    @Override
    public boolean deleteFile(String folderPath) {
        return false;
    }

    @Override
    public boolean exitsFile(String filePath) {
        return false;
    }

    @Override
    public boolean mkdirsNotExits(String filePath) {
        return false;
    }

    @Override
    public boolean mkdirs(String filePath) {
        return false;
    }

    @Override
    public void copyFile(InputStream inputStream, String filePath) throws IOException {}

    @Override
    public boolean mergeChunks(String chunkFileFolderPath, String filePath, Integer totalChunks) {
        return false;
    }

    @Override
    public String getType() {
        return FileStrategy.OSS.getType();
    }
}
