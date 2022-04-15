package com.lyq.file.service.impl;

import com.lyq.file.dto.es.FileDOC;
import com.lyq.file.dto.web.FilePageVO;
import com.lyq.file.dto.web.FileVO;
import com.lyq.file.mapstruct.FileDocMapper;
import com.lyq.file.repository.FIleDocRepository;
import com.lyq.file.service.IFIleSearchService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
    public FilePageVO<FileVO> search(String keyword, Pageable pageable) {

        if (StringUtils.isBlank(keyword)) {
            Page<FileDOC> all = repository.findAll(pageable);

            List<FileVO> collect = all.getContent()
                    .stream()
                    .map(FileDocMapper.INSTANCE::doc2Vo)
                    .collect(Collectors.toList());

            return new FilePageVO<>(
                    pageable.getPageNumber(),
                    pageable.getPageSize(),
                    all.getTotalElements(),
                    collect);
        }

        Criteria criteria = new Criteria();
        criteria.and(new Criteria("fileName").matches(keyword));

        CriteriaQuery criteriaQuery = new CriteriaQuery(criteria).setPageable(pageable);
        SearchHits<FileDOC> searchHits = restTemplate.search(criteriaQuery, FileDOC.class);

        List<FileDOC> collectDocs = searchHits.get()
                .map(SearchHit::getContent)
                .collect(Collectors.toList());

        List<FileVO> collectVO = collectDocs
                .stream()
                .map(FileDocMapper.INSTANCE::doc2Vo)
                .collect(Collectors.toList());

        return new FilePageVO<>(
                pageable.getPageNumber(),
                pageable.getPageSize(),
                searchHits.getTotalHits(),
                collectVO);
    }


    @Override
    public void addFile(FileDOC fileDOC) {
        FileDOC save = restTemplate.save(fileDOC);
    }
}
