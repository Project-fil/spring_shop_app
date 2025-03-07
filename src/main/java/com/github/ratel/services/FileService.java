package com.github.ratel.services;

import com.github.ratel.entity.FileEntity;

public interface FileService {

    FileEntity create(FileEntity fileEntity);

    void deleteById(Long id);

}
