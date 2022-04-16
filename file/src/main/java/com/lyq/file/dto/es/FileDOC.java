package com.lyq.file.dto.es;


import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;



@Document(indexName = "search_file")
@ToString
@Builder
@Data
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

}
