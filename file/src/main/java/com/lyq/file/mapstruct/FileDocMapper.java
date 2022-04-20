package com.lyq.file.mapstruct;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface FileDocMapper {

    FileDocMapper INSTANCE = Mappers.getMapper(FileDocMapper.class);


}
