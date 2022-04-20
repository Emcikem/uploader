package com.lyq.file;

import com.lyq.file.dto.entity.FIlePO;
import com.lyq.file.repository.FilePoRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.util.Random;


public class FileOperationTest extends BaseTest {

    @Autowired
    private FilePoRepository poMapper;


    @Test
    public void change() {
//        poMapper.reNameByIdentifier("akjfha", "陈强就是个傻逼");
//        poMapper.deleteByIdentifier("akjfha");

    }

    @Test
    public void save() {
//        Random random = new Random();
//        for (int i = 1; i <= 0; i++) {
//            FIlePO po = FIlePO.builder()
//                    .id(i)
//                    .identifier(NameUtils.getEnName())
//                    .filename(NameUtils.getCnName())
//                    .totalSize((long)Math.abs(random.nextInt(1000000)))
//                    .createTime(System.currentTimeMillis())
//                    .updateTime(System.currentTimeMillis())
//                    .filePath("/root")
//                    .fileType("jpg")
//                    .deleted(0)
//                    .build();
//            poMapper.save(po);
//        }
    }

    @Test
    public void query() {
//        FIlePO identifier = poMapper.queryByIdentifier("keli");
//        System.out.println(identifier);
    }


}
