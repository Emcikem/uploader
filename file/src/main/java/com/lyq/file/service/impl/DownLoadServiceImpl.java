package com.lyq.file.service.impl;

import com.lyq.file.repository.FilePoRepository;
import com.lyq.file.service.IDownLoadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;

@Service
public class DownLoadServiceImpl implements IDownLoadService {

    @Autowired
    private FilePoRepository filePoRepository;

    @Override
    public void download(String identifier, HttpServletRequest req, HttpServletResponse resp) throws IOException {

        String relationPath = filePoRepository.queryPathByIdentifier(identifier);
        String path = ResourceUtils.getURL("classpath:").getPath();
        String filePath = path + "file" + relationPath;
        InputStream inputStream = new FileInputStream(filePath);
        resp.reset();
        resp.setContentType("application/octet-stream");
        String filename = new File(filePath).getName();
        resp.addHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(filename, "UTF-8"));
        ServletOutputStream outputStream = resp.getOutputStream();
        byte[] b = new byte[1024];
        int len;
        while ((len = inputStream.read()) > 0) {
            outputStream.write(b, 0, len);
        }
        inputStream.close();
    }
}
