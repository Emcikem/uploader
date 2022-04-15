package com.lyq.file.service;

import com.lyq.file.dto.es.FileDOC;
import com.lyq.file.dto.web.FilePageVO;
import com.lyq.file.dto.web.FileVO;
import org.springframework.data.domain.Pageable;

public interface IFIleSearchService {

    FilePageVO<FileVO> search(String keyword, Pageable pageable);

    void addFile(FileDOC fileDOC);

}
