package com.lyq.fileuploader.service.impl;

import com.lyq.fileuploader.dto.es.FileDOC;
import com.lyq.fileuploader.dto.web.FileVO;
import com.lyq.fileuploader.mapstruct.FileDocMapper;
import com.lyq.fileuploader.repository.FIleDocRepository;
import com.lyq.fileuploader.service.IFIleSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FileSearchServiceImpl implements IFIleSearchService {

    @Autowired
    private ElasticsearchRestTemplate restTemplate;

    @Autowired
    private FIleDocRepository repository;

    @Override
    public Page<FileVO> search(String keyword, Pageable pageable) {

        Criteria criteria = new Criteria();
        criteria.and(new Criteria("fileName").matches(keyword));

        CriteriaQuery criteriaQuery = new CriteriaQuery(criteria).setPageable(pageable);
        SearchHits<FileDOC> searchHits = restTemplate.search(criteriaQuery, FileDOC.class);

        List<FileDOC> collectDocs = searchHits.get()
                .map(SearchHit::getContent)
                .collect(Collectors.toList());

        Page<FileDOC> docPage = new PageImpl<>(collectDocs, pageable, searchHits.getTotalHits());
        return docPage.map(FileDocMapper.INSTANCE::doc2Vo);
    }


    @Override
    public void addFile(FileDOC fileDOC) {
        FileDOC save = restTemplate.save(fileDOC);
    }
}
