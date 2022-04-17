package com.lyq.file;

import com.lyq.file.dto.StrategyConfigDTO;
import com.lyq.file.service.StrategyService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class StrategyTest extends BaseTest{

    @Autowired
    private StrategyService strategyService;

    @Test
    public void initStrategy() {
        StrategyConfigDTO strategyConfigDTO = StrategyConfigDTO.builder()
                .shouldMerge(false)
                .folderPath("file")
                .storeType("LOCAL")
                .build();
        strategyService.saveStrategyConfig(strategyConfigDTO);
    }

    @Test
    public void queryStrategy() {
        System.out.println(strategyService.queryStrategy());
    }
}
