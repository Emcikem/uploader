package com.lyq.fileuploader.controller;

import com.lyq.fileuploader.response.RestApiResponse;
import com.lyq.fileuploader.service.StrategyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("strategy")
public class StrategyController {


    @Autowired
    private StrategyService strategyService;

    /**
     *
     */
    public RestApiResponse<Object> changeStrategy (String type) {
        strategyService.changeStrategy(type);
        return RestApiResponse.success();
    }
}
