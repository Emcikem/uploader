package com.lyq.fileuploader;

import com.lyq.fileuploader.service.StrategyService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class StrategyTest extends BaseTest{

    @Autowired
    private StrategyService strategyService;

    @Test
    public void initStrategy() {
        strategyService.changeStoreStrategy("LOCAL");
        strategyService.changeFolderPath("/Users/emcikem/Desktop");
        strategyService.isMergeStore(true);
    }

    @Test
    public void queryStrategy() {
        System.out.println(strategyService.getStrategy());
    }

}
