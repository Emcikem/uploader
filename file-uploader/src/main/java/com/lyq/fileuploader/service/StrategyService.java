package com.lyq.fileuploader.service;


import com.lyq.fileuploader.dto.StrategyConfigDTO;

public interface StrategyService {

    void changeStoreStrategy(String type);

    void changeFolderPath(String path);

    void isMergeStore(Boolean isMergeStore);

    StrategyConfigDTO getStrategy();
}
