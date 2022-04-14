package com.lyq.fileuploader.service;


import com.lyq.fileuploader.dto.StrategyConfigDTO;

public interface StrategyService {

    StrategyConfigDTO queryStrategy();

    void saveStrategyConfig(StrategyConfigDTO config);
}
