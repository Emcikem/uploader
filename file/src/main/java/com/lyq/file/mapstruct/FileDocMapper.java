package com.lyq.file.mapstruct;

import com.lyq.file.dto.es.FileDOC;
import com.lyq.file.dto.web.FileVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface FileDocMapper {

    FileDocMapper INSTANCE = Mappers.getMapper(FileDocMapper.class);

    FileVO doc2Vo(FileDOC fileDOC);

}
