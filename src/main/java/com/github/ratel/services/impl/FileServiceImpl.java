package com.github.ratel.services.impl;

import com.github.ratel.entity.FileEntity;
import com.github.ratel.repositories.FileRepository;
import com.github.ratel.services.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {

    private final FileRepository fileRepository;


    @Override
    public FileEntity create(FileEntity fileEntity) {
        return this.fileRepository.save(fileEntity);
    }

    @Override
    public void deleteById(long id) {
        this.fileRepository.deleteById(id);
    }
}
