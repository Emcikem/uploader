package com.lyq.fileuploader.dto.web;


import lombok.Builder;
import lombok.ToString;

import java.util.List;

@ToString
@Builder
public class FilePageVO<T> {

    private Integer pageNo;

    private Integer pageSize;

    private Long total;

    private List<T> content;

    public FilePageVO(Integer pageNo, Integer pageSize, Long total, List<T> content) {
        this.pageNo = pageNo;
        this.pageSize = pageSize;
        this.total = total;
        this.content = content;
    }

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public List<T> getContent() {
        return content;
    }

    public void setContent(List<T> content) {
        this.content = content;
    }
}
