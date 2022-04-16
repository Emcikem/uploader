package com.lyq.file.service.impl;

import com.lyq.file.repository.FilePoRepository;
import com.lyq.file.service.IFileOperationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FileOperationServiceImpl implements IFileOperationService {

    @Autowired
    private FilePoRepository filePoRepository;

    @Override
    public void reName(String identifier, String name) {
        filePoRepository.reNameByIdentifier(identifier, name);
    }

    @Override
    public void delete(String identifier) {
        filePoRepository.deleteByIdentifier(identifier);
    }

    @Override
    public void deletes(List<String> identifier) {
        filePoRepository.deleteByIdentifiers(identifier);
    }
}
