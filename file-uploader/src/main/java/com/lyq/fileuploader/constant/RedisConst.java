package com.lyq.fileuploader.constant;

import java.util.ArrayList;
import java.util.List;

public class RedisConst {



    /**
     * 策略
     */
    public static final String STRATEGY = "strategy";
    /**
     * 存储方式
     */
    public static final String STORESTRATEGY = "storeStrategy";
    /**
     * 文件存储位置
     */
    public static final String UPLOADERFOLDER = "uploadFolder";
    /**
     * 是否合并分片存储
     */
    public static final String ISMERGESTORE = "isMergeStore";




    // 分片信息

    /**
     * 已上传的分片下标集合
     */
    public static final String UPLOADED = "uploaded";
    /**
     * 分片数
     */
    public static final String TOTALCHUNKS = "totalChunks";
    /**
     * 总文件大小
     */
    public static final String TOTALSIZE = "totalSize";
    /**
     * 文件名称
     */
    public static final String NAME = "name";
    /**
     * 是否上传完毕
     */
    public static final String ISUPLOADED = "isUploaded";

}
