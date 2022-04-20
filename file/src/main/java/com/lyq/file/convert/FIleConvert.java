package com.lyq.file.convert;

import com.lyq.file.dto.es.FileDOC;
import com.lyq.file.dto.web.FileVO;

import java.time.ZoneOffset;

public class FIleConvert {

    public static FileVO doc2Vo(FileDOC fileDOC) {
        if ( fileDOC == null ) {
            return null;
        }

        FileVO fileVO = new FileVO();

        fileVO.setFileName( fileDOC.getFileName() );
        fileVO.setIdentifier( fileDOC.getIdentifier() );
        fileVO.setTotalSize( fileDOC.getTotalSize() );
        fileVO.setUpdateTime( fileDOC.getUpdateTime());
        return fileVO;
    }
}
