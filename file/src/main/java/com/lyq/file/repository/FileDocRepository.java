package com.lyq.file.repository;

import com.lyq.file.dto.es.FileDOC;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileDocRepository extends ElasticsearchRepository<FileDOC, Long> {
}
