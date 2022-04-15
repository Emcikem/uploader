package com.lyq.file;

import com.lyq.file.service.IFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 工厂模式动态配置接口实现类
 */
@Service
public class FileServiceStrategy {

    Map<String, IFileService> fileServiceMap;

    @Autowired
    public FileServiceStrategy(List<IFileService> fileServiceList) {
        fileServiceMap = fileServiceList.stream().collect(Collectors.toMap(IFileService::getType, Function.identity()));
    }

    public IFileService getStrategy(String type) {
        return fileServiceMap.get(type);
    }

}
