package com.lyq.fileuploader.mapstruct;

import com.lyq.fileuploader.dto.es.FileDOC;
import com.lyq.fileuploader.dto.web.FileVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface FileDocMapper {

    FileDocMapper INSTANCE = Mappers.getMapper(FileDocMapper.class);

    FileVO doc2Vo(FileDOC fileDOC);

}
