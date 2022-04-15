package com.lyq.file.enume;

import lombok.Getter;

@Getter
public enum FileStrategy {

    LOCAL("LOCAL", "本地存储"),
    OSS("OSS", "OSS存储");

    /**
     * 存储类型
     */
    private String type;
    /**
     * 备注
     */
    private String desc;


    FileStrategy(String type, String desc) {
        this.type = type;
        this.desc = desc;
    }
}
