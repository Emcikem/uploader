package com.lyq.file.controller;

import com.lyq.file.dto.web.FilePageVO;
import com.lyq.file.dto.web.FileVO;
import com.lyq.file.response.RestApiResponse;
import com.lyq.file.service.IFIleSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("search")
public class SearchController {

    @Autowired
    private IFIleSearchService searchService;

    @GetMapping("/fileList")
    public RestApiResponse<Object> searchFile(Integer pageNo, Integer pageSize, String keyWord) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        FilePageVO<FileVO> search = searchService.search(keyWord, pageable);
        return RestApiResponse.success(search);
    }
}
