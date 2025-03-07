package com.github.ratel.services.impl;

import com.github.ratel.entity.FileEntity;
import com.github.ratel.exceptions.AppException;
import com.github.ratel.repositories.FileRepository;
import com.github.ratel.services.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {

    private final FileRepository fileRepository;

    @Override
    @Transactional
    public FileEntity create(FileEntity fileEntity) {
        if (Objects.isNull(fileEntity)) {
            throw new AppException("Invalid parameters value: fileEntity(%s)", fileEntity);
        }
        return this.fileRepository.save(fileEntity);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        if (Objects.isNull(id)) {
            throw new AppException("Invalid parameters value: id(%s)", id);
        }
        this.fileRepository.deleteById(id);
    }

}
