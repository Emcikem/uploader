package com.lyq.file.service.impl;

import com.lyq.file.FileServiceStrategy;
import com.lyq.file.constant.RedisConst;
import com.lyq.file.dto.entity.FIlePO;
import com.lyq.file.repository.FilePoRepository;
import com.lyq.file.service.IFileOperationService;
import com.lyq.file.service.IFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class FileOperationServiceImpl implements IFileOperationService {


    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Resource
    private FileServiceStrategy strategy;

    @Autowired
    private FilePoRepository filePoRepository;

    @Override
    public void reName(String identifier, String name) {
        filePoRepository.reNameByIdentifier(identifier, name, System.currentTimeMillis());
    }

    @Override
    public void delete(String identifier) {
        FIlePO fIlePO = filePoRepository.queryByIdentifier(identifier);
        if (fIlePO == null) {
            return;
        }
        // 删除缓存，删除数据库，删除文件
        redisTemplate.opsForHash().delete(identifier, RedisConst.UPLOADED);
        filePoRepository.deleteByIdentifier(identifier);
        getFileStrategy().deleteFile(fIlePO.getFilePath());
    }

    @Override
    public void deletes(List<String> identifier) {
        filePoRepository.deleteByIdentifiers(identifier);
    }


    /**
     * 从redis的业务数据里获取存储策略，通过策略模式返回策略接口
     */
    private IFileService getFileStrategy () {
        String type = (String) redisTemplate.opsForHash().get(RedisConst.STRATEGY, RedisConst.STORETYPE);
        type = type == null ? "LOCAL" : type;
        return strategy.getStrategy(type);
    }
}
