package com.lyq.fileuploader;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@SpringBootApplication
public class FileUploaderApplication {

    public static void main(String[] args) {
        SpringApplication.run(FileUploaderApplication.class, args);
    }

}
