package com.lyq.fileuploader;

import com.lyq.fileuploader.dto.es.FileDOC;
import com.lyq.fileuploader.dto.web.FilePageVO;
import com.lyq.fileuploader.dto.web.FileVO;
import com.lyq.fileuploader.mapstruct.FileDocMapper;
import com.lyq.fileuploader.service.IFIleSearchService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.io.UnsupportedEncodingException;
import java.util.Random;

public class ElasticSearchTest extends BaseTest{

    @Autowired
    private IFIleSearchService ifIleSearchService;

    @Test
    public void search() {
        Pageable pageable =  PageRequest.of(0, 10);
        FilePageVO<FileVO> search = ifIleSearchService.search("牛赛", pageable);
        System.out.println(search);
    }

    @Test
    public void mapstruct() {

    }
    //随机生成汉字
    private static char getRandomChar() {
        String str = "";
        int hightPos; //
        int lowPos;
        Random random = new Random();

        hightPos = (176 + Math.abs(random.nextInt(39)));
        lowPos = (161 + Math.abs(random.nextInt(93)));

        byte[] b = new byte[2];
        b[0] = (Integer.valueOf(hightPos)).byteValue();
        b[1] = (Integer.valueOf(lowPos)).byteValue();

        try {
            str = new String(b, "GBK");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            System.out.println("错误");
        }
        return str.charAt(0);
    }


    @Test
    public void save() {
        Random random = new Random();
        for (int i = 1; i <= 1000; i++) {
            StringBuilder fileName = new StringBuilder();
            for (int j = 0; j < random.nextInt(50); j++) {
                fileName.append(getRandomChar());
            }
            FileDOC doc = FileDOC.builder()
                    .id((long) i)
                    .fileName(fileName.toString())
                    .identifier("111")
                    .totalSize(Math.abs(random.nextLong()))
                    .build();
            ifIleSearchService.addFile(doc);
            System.out.println(doc);
        }
    }
}
