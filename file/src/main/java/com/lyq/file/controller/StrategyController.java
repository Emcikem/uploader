package com.lyq.file.controller;

import com.lyq.file.dto.StrategyConfigDTO;
import com.lyq.file.response.RestApiResponse;
import com.lyq.file.service.StrategyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("strategy")
public class StrategyController {


    @Autowired
    private StrategyService strategyService;



    @GetMapping("query")
    public RestApiResponse<Object> queryStrategy() {
        StrategyConfigDTO strategy = strategyService.queryStrategy();
        return RestApiResponse.success(strategy);
    }

    @PostMapping("save")
    public RestApiResponse<Object> saveStrategy(@RequestBody StrategyConfigDTO configDTO) {
        strategyService.saveStrategyConfig(configDTO);
        return RestApiResponse.success();
    }
}
