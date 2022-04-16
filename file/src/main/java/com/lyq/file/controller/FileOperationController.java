package com.lyq.file.controller;

import com.lyq.file.response.RestApiResponse;
import com.lyq.file.service.IFileOperationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/option")
@RestController
public class FileOperationController {

    @Autowired
    private IFileOperationService operationService;


    @GetMapping("/reName")
    public RestApiResponse<Object> reName(String identifier, String name) {
        operationService.reName(identifier, name);
        return RestApiResponse.success();
    }

    @DeleteMapping("/delete")
    public RestApiResponse<Object> delete(String identifier) {
        operationService.delete(identifier);
        return RestApiResponse.success();
    }

    @DeleteMapping("/deletes")
    public RestApiResponse<Object> deletes(List<String> identifiers) {
        operationService.deletes(identifiers);
        return RestApiResponse.success();
    }
}
