package com.lyq.file.dto.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;

@Data
@ToString
@Entity(name = "tbl_file")
@Builder
@AllArgsConstructor
public class FIlePO implements Serializable {


    private static final long serialVersionUID = 1052105662825488174L;
    /**
     * id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 文件的md5
     */
    @Column(name = "identifier", nullable = false)
    private String identifier;

    /**
     * 文件名
     */
    @Column(name = "file_name", nullable = false)
    private String filename;

    /**
     * 文件后缀
     */
    @Column(name = "file_type", nullable = false)
    private String fileType;

    /**
     * 文件大小
     */
    @Column(name = "total_size")
    private Long totalSize;

    /**
     * 文件存储路径
     */
    @Column(name = "file_path", nullable = false)
    private String filePath;

//    /**
//     * 创建者username
//     */
//    @Column(name = "creator", nullable = false)
//    private String creator;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Long createTime;

    /**
     * 更新日期
     */
    @Column(name = "update_time")
    private Long updateTime;

    /**
     * 状态(可用0/已删除1状态)
     */
    @Column(name = "deleted", nullable = false)
    private int deleted;

    public FIlePO() {

    }
}
