package com.lyq.file.service.impl;

import com.lyq.file.dto.entity.FIlePO;
import com.lyq.file.repository.FilePoRepository;
import com.lyq.file.service.IDownLoadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;

@Service
public class DownLoadServiceImpl implements IDownLoadService {

    @Autowired
    private FilePoRepository filePoRepository;

    @Override
    public void download(String identifier, boolean download, HttpServletRequest req, HttpServletResponse resp) throws IOException {

        FIlePO fIlePO = filePoRepository.queryByIdentifier(identifier);
        if (fIlePO == null) {
            throw new IOException("没有文件");
        }

        InputStream inputStream = new FileInputStream(fIlePO.getFilePath());
        OutputStream out = resp.getOutputStream();

        //使用URLEncoder来防止文件名乱码或者读取错误，并且设置下载
        if (download) {
            resp.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(fIlePO.getFilename(), "UTF-8"));
            resp.setContentType("application/force-download");
        }
        int b;
        byte[] buffer = new byte[1024];
        while ((b = inputStream.read(buffer)) > 0) {
            out.write(buffer, 0, b);
        }
        inputStream.close();
        out.close();
    }
}
