package com.lyq.fileuploader;

import com.lyq.fileuploader.dto.StrategyConfigDTO;
import com.lyq.fileuploader.service.StrategyService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class StrategyTest extends BaseTest{

    @Autowired
    private StrategyService strategyService;

    @Test
    public void initStrategy() {
        StrategyConfigDTO strategyConfigDTO = StrategyConfigDTO.builder()
                .shouldMerge(true)
                .folderPath("/Users/emcikem/Desktop")
                .storeType("LOCAL")
                .build();
        strategyService.saveStrategyConfig(strategyConfigDTO);
    }

    @Test
    public void queryStrategy() {
        System.out.println(strategyService.queryStrategy());
    }

}
