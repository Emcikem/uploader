package com.lyq.fileuploader.service;

import com.lyq.fileuploader.dto.es.FileDOC;
import com.lyq.fileuploader.dto.web.FilePageVO;
import com.lyq.fileuploader.dto.web.FileVO;
import org.springframework.data.domain.Pageable;

public interface IFIleSearchService {

    FilePageVO<FileVO> search(String keyword, Pageable pageable);

    void addFile(FileDOC fileDOC);

}
