package com.lyq.file.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface IDownLoadService {

    void download(String identifier, boolean download, HttpServletRequest req, HttpServletResponse resp) throws IOException;
}
