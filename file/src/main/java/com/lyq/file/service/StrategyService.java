package com.lyq.file.service;


import com.lyq.file.dto.StrategyConfigDTO;

public interface StrategyService {

    StrategyConfigDTO queryStrategy();

    void saveStrategyConfig(StrategyConfigDTO config);
}
