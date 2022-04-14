package com.lyq.fileuploader.dto.es;


import lombok.Builder;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;



@Document(indexName = "search_file")
@ToString
@Builder
public class FileDOC {

    @Id
    private Long id;
    /**
     * 文件名
     */
    @Field(type = FieldType.Text)
    private String fileName;
    /**
     * 文件md5
     */
    @Field(type = FieldType.Text)
    private String identifier;
    /**
     * 文件总大小
     */
    @Field(type = FieldType.Long)
    private Long totalSize;

    /**
     * 文件更新时间
     */
    @Field(type = FieldType.Long)
    private Long updateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public Long getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(Long totalSize) {
        this.totalSize = totalSize;
    }
}
