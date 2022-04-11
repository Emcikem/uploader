package com.lyq.fileuploader.dto;

import lombok.Builder;
import lombok.ToString;

@ToString
@Builder
public class StrategyConfigDTO {

    /**
     * 存储策略
     */
    private String storeStrategy;

    /**
     * 存储路径
     */
    private String folderPath;

    /**
     * 是否合并分片存储
     */
    private Boolean isMerge;

    public String getStoreStrategy() {
        return storeStrategy;
    }

    public void setStoreStrategy(String storeStrategy) {
        this.storeStrategy = storeStrategy;
    }

    public String getFolderPath() {
        return folderPath;
    }

    public void setFolderPath(String folderPath) {
        this.folderPath = folderPath;
    }

    public Boolean getChunks() {
        return isMerge;
    }

    public void setChunks(Boolean chunks) {
        isMerge = chunks;
    }
}
