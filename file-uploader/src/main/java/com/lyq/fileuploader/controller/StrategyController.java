package com.lyq.fileuploader.controller;

import com.lyq.fileuploader.response.RestApiResponse;
import com.lyq.fileuploader.service.StrategyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("strategy")
public class StrategyController {


    @Autowired
    private StrategyService strategyService;

    /**
     * 改成存储的策略
     */
    @GetMapping("storeStrategy")
    public RestApiResponse<Object> changeStoreStrategy (String type) {
        strategyService.changeStoreStrategy(type);
        return RestApiResponse.success();
    }

    /***
     * 改变存储的地址
     */
    @GetMapping("changePath")
    public RestApiResponse<Object> changeFolderPath (String path) {
        strategyService.changeFolderPath(path);
        return RestApiResponse.success();
    }

    /***
     * 是否分片存储
     */
    @GetMapping("isMerge")
    public RestApiResponse<Object> isMergeStore (Boolean isMerge) {
        strategyService.isMergeStore(isMerge);
        return RestApiResponse.success();
    }
}
