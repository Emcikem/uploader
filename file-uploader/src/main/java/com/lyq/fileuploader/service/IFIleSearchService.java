package com.lyq.fileuploader.service;

import com.lyq.fileuploader.dto.es.FileDOC;
import com.lyq.fileuploader.dto.web.FileVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IFIleSearchService {

    Page<FileVO> search(String keyword, Pageable pageable);

    void addFile(FileDOC fileDOC);

}
