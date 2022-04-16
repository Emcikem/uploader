package com.lyq.file.service;

import java.util.List;

public interface IFileOperationService {

    void reName(String identifier, String name);

    void delete(String identifier);

    void deletes(List<String> identifiers);
}
