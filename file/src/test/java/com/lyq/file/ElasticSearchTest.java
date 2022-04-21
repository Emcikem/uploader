package com.lyq.file;

import com.lyq.file.dto.web.FilePageVO;
import com.lyq.file.dto.web.FileVO;
import com.lyq.file.repository.FileDocRepository;
import com.lyq.file.service.IFIleSearchService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;

import java.io.UnsupportedEncodingException;
import java.util.Random;

public class ElasticSearchTest extends BaseTest{

    @Autowired
    private IFIleSearchService ifIleSearchService;

    @Test
    public void search() {
        Pageable pageable =  PageRequest.of(0, 100);
        FilePageVO<FileVO> search = ifIleSearchService.search("", pageable);
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
//        Random random = new Random();
//        for (int i = 0; i < 0; i++) {
//            FileDOC fileDOC = FileDOC.builder()
//                    .id((long) i)
//                    .fileName(NameUtils.getEnName() + ".jpg")
//                    .identifier(NameUtils.getCnName())
//                    .totalSize((long) Math.abs(random.nextInt(100)))
//                    .updateTime(System.currentTimeMillis())
//                    .build();
//            ifIleSearchService.addFile(fileDOC);
//        }
    }

    @Autowired
    private ElasticsearchRestTemplate template;
    @Autowired
    private FileDocRepository repository;


    @Test
    public void delete() {
//        repository.deleteAll();
    }
}
