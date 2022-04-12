package com.lyq.fileuploader;

import com.lyq.fileuploader.dto.es.FileDOC;
import com.lyq.fileuploader.dto.web.FileVO;
import com.lyq.fileuploader.mapstruct.FileDocMapper;
import com.lyq.fileuploader.service.IFIleSearchService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

public class ElasticSearchTest extends BaseTest{

    @Autowired
    private IFIleSearchService ifIleSearchService;

    @Test
    public void search() {
        Pageable pageable =  PageRequest.of(0, 10);
        Page<FileVO> search = ifIleSearchService.search("牛赛", pageable);
        search.forEach(System.out::println);
    }

    @Test
    public void mapstruct() {
        FileDOC doc = FileDOC.builder()
                .id(1L)
                .fileName("牛客竞赛")
                .identifier("111")
                .build();
        FileVO fileVO = FileDocMapper.INSTANCE.doc2Vo(doc);
        System.out.println(fileVO);
    }

    @Test
    public void save() {
        FileDOC fileDOC = FileDOC.builder()
                .fileName("牛客竞赛")
                .identifier("221121")
                .id(1L)
                .totalSize(2121L)
                .build();
        ifIleSearchService.addFile(fileDOC);
    }
}
