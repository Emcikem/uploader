package com.lyq.file.service.impl;

import com.lyq.file.constant.RedisConst;
import com.lyq.file.dto.StrategyConfigDTO;
import com.lyq.file.service.StrategyService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Service
public class StrategyServiceImpl implements StrategyService {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public StrategyConfigDTO queryStrategy() {
        Map<Object, Object> entries = redisTemplate.opsForHash().entries(RedisConst.STRATEGY);

        return StrategyConfigDTO.builder()
                .storeType((String) entries.get(RedisConst.STORETYPE))
                .folderPath((String) entries.get(RedisConst.UPLOADERFOLDER))
                .shouldMerge(Boolean.parseBoolean((String) entries.get(RedisConst.ISMERGESTORE)))
                .build();
    }

    @Override
    public void saveStrategyConfig(StrategyConfigDTO config) {
        Map<String, Object> map = new HashMap<>();
        map.put(RedisConst.ISMERGESTORE, String.valueOf(config.getShouldMerge()));
        map.put(RedisConst.UPLOADERFOLDER, config.getFolderPath());
        map.put(RedisConst.STORETYPE, config.getStoreType());
        redisTemplate.opsForHash().putAll(RedisConst.STRATEGY, map);
    }

}
