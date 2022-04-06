package com.lyq.fileuploader.service;

import java.io.IOException;
import java.io.InputStream;

/**
 * 文件操作接口
 * 目的是为了适配多个存储端，在配置中配置存储
 * Local：本地存储
 * OSS：oss存储
 */
public interface IFileService {


    /**
     * 删除文件夹
     */
    void deleteDirectory(String folderPath);


    /**
     * 判断文件是否存在
     */
    boolean exitsFile(String filePath);

    /**
     * 创建文件夹(不存在文件)
     */
    boolean mkdirsNotExits(String filePath);

    /**
     * 创建文件夹
     */
    boolean mkdirs(String filePath);

    /**
     * 把文件从内存存到磁盘中
     */
    void copyFile(InputStream inputStream, String filePath) throws IOException;

    /**
     * 合并分片
     */
    boolean mergeChunks(String chunkFileFolderPath, String filePath, Integer totalChunks);
}
