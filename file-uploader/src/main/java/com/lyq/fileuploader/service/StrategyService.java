package com.lyq.fileuploader.service;


public interface StrategyService {

    void changeStoreStrategy(String type);

    void changeFolderPath(String path);
}
