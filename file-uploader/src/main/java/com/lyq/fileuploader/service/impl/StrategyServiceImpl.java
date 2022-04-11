package com.lyq.fileuploader.service.impl;

import com.lyq.fileuploader.constant.RedisConst;
import com.lyq.fileuploader.dto.StrategyConfigDTO;
import com.lyq.fileuploader.service.StrategyService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

@Service
public class StrategyServiceImpl implements StrategyService {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public void changeStoreStrategy(String type) {
        redisTemplate.opsForHash().put(RedisConst.STRATEGY, RedisConst.STORESTRATEGY, type);
    }

    @Override
    public void changeFolderPath(String path) {
        redisTemplate.opsForHash().put(RedisConst.STRATEGY, RedisConst.UPLOADERFOLDER, path);
    }

    @Override
    public void isMergeStore(Boolean isMergeStore) {
        redisTemplate.opsForHash().put(RedisConst.STRATEGY, RedisConst.ISMERGESTORE, String.valueOf(isMergeStore));
    }

    @Override
    public StrategyConfigDTO getStrategy() {
        Map<Object, Object> entries = redisTemplate.opsForHash().entries(RedisConst.STRATEGY);

        return StrategyConfigDTO.builder()
                .storeStrategy((String) entries.get(RedisConst.STORESTRATEGY))
                .folderPath((String) entries.get(RedisConst.UPLOADERFOLDER))
                .isMerge(Boolean.parseBoolean((String) entries.get(RedisConst.ISMERGESTORE)))
                .build();
    }

}
