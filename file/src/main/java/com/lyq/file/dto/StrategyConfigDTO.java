package com.lyq.file.dto;

import lombok.Builder;
import lombok.ToString;

import java.io.Serializable;

@ToString
@Builder
public class StrategyConfigDTO implements Serializable {

    private static final long serialVersionUID = 2160977717064646184L;
    /**
     * 存储策略
     */
    private String storeType;

    /**
     * 存储路径
     */
    private String folderPath;

    /**
     * 是否合并分片存储
     */
    private Boolean shouldMerge;

    public String getStoreType() {
        return storeType;
    }

    public void setStoreType(String storeType) {
        this.storeType = storeType;
    }

    public String getFolderPath() {
        return folderPath;
    }

    public void setFolderPath(String folderPath) {
        this.folderPath = folderPath;
    }

    public Boolean getShouldMerge() {
        return shouldMerge;
    }

    public void setShouldMerge(Boolean shouldMerge) {
        this.shouldMerge = shouldMerge;
    }
}
