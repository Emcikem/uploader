package com.lyq.fileuploader.service.impl;

import com.lyq.fileuploader.enume.FileStrategy;
import com.lyq.fileuploader.service.IFileService;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.Arrays;
import java.util.Comparator;

@Service
public class LocalFileServiceImpl implements IFileService {

    @Override
    public void deleteDirectory(String folderPath) {
        if (!folderPath.endsWith(File.separator)) {
            folderPath = folderPath + File.separator;
        }
        File folder = new File(folderPath);
        if (folder.exists() && folder.isDirectory()) {
            File[] files = folder.listFiles();
            if (files != null) {
                Arrays.stream(files).forEach(file -> {
                    if (file.isFile()) {
                        file.delete();
                    } else {
                        deleteDirectory(file.getPath());
                    }
                });
            }
            folder.delete();
        }
    }

    @Override
    public boolean exitsFile(String filePath) {
        File file = new File(filePath);
        return file.exists();
    }

    @Override
    public boolean mkdirsNotExits(String filePath) {
        File file = new File(filePath);
        return file.mkdirs();
    }

    @Override
    public boolean mkdirs(String filePath) {
        File file = new File(filePath);
        if (!file.exists()) {
            return file.mkdirs();
        }
        return false;
    }

    @Override
    public void copyFile(InputStream inputStream, String filePath) throws IOException {
        FileOutputStream outputStream = new FileOutputStream(filePath);
        IOUtils.copy(inputStream, outputStream);
    }

    @Override
    public boolean mergeChunks(String chunkFileFolderPath, String filePath, Integer totalChunks) {
        if (checkChunks(chunkFileFolderPath, totalChunks)) {
            File chunkFileFolder = new File(chunkFileFolderPath);
            File mergeFile = new File(filePath);
            File[] chunks = chunkFileFolder.listFiles();
            if (chunks == null) {
                return false;
            }
            // 排序
            Arrays.sort(chunks, Comparator.comparing(File::getName));
            try {
                RandomAccessFile randomAccessFileWriter = new RandomAccessFile(mergeFile, "rw");
                byte[] bytes = new byte[1024];
                for (File chunk : chunks) {
                    RandomAccessFile randomAccessFileReader = new RandomAccessFile(chunk, "r");
                    int len;
                    while ((len = randomAccessFileReader.read(bytes)) != -1) {
                        randomAccessFileWriter.write(bytes, 0, len);
                    }
                    randomAccessFileReader.close();
                }
                randomAccessFileWriter.close();
            } catch (IOException e) {
                return false;
            }
            return true;
        }
        return false;
    }

    @Override
    public String getType() {
        return FileStrategy.LOCAL.getType();
    }

    /**
     * 检查所有分片是否存在
     */
    private boolean checkChunks(String chunkFileFolderPath, Integer totalChunks) {
        for (int i = 1; i <= totalChunks + 1; i++) {
            File file = new File(chunkFileFolderPath + File.separator + i);
            if (!file.exists()) {
                return false;
            }
        }
        return true;
    }
}
