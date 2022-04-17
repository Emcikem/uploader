package com.lyq.file.service.impl;

import com.lyq.file.repository.FilePoRepository;
import com.lyq.file.service.IDownLoadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;

@Service
public class DownLoadServiceImpl implements IDownLoadService {

    @Autowired
    private FilePoRepository filePoRepository;

    @Override
    public void download(String identifier, HttpServletRequest req, HttpServletResponse resp) throws IOException {

        String relationPath = filePoRepository.queryPathByIdentifier(identifier);

        resp.setContentType("application/octet-stream");

        // 获取 resources 根目录下的 的流
        ClassPathResource resource = new ClassPathResource(relationPath);
        String filename = resource.getFile().getName();
        String path = resource.getFile().getPath();

        resp.addHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(filename, "UTF-8"));

        InputStream inputStream = resource.getInputStream();
        ServletOutputStream outputStream = resp.getOutputStream();
        byte[] b = new byte[1024];
        int len;
        while ((len = inputStream.read()) > 0) {
            outputStream.write(b, 0, len);
        }
        inputStream.close();
    }
}
