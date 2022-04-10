package com.lyq.fileuploader.service.impl;

import com.lyq.fileuploader.constant.RedisConst;
import com.lyq.fileuploader.service.StrategyService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class StrategyServiceImpl implements StrategyService {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public void changeStrategy(String type) {
        redisTemplate.opsForHash().put(RedisConst.STRATEGY, RedisConst.STORE, type);
    }
}
