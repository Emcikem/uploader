package com.lyq.file.controller;

import com.lyq.file.response.RestApiResponse;
import com.lyq.file.service.IDownLoadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@RestController
@RequestMapping("download")
public class DownLoadController {

    @Resource
    private IDownLoadService downLoadService;

    @GetMapping("/direct")
    public RestApiResponse<Object> doGet(String identifier, boolean download, HttpServletRequest req, HttpServletResponse resp) {
        try {
            downLoadService.download(identifier, download, req, resp);
            return RestApiResponse.success();
        } catch (IOException e) {
            return RestApiResponse.error(e.toString());
        }
    }
}
