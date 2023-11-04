package com.github.ratel.handlers;

import com.github.ratel.entity.FileEntity;
import com.github.ratel.exceptions.FileTypeException;
import com.github.ratel.utils.ImageCompressor;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Slf4j
@Component
public class FileHandler {

    @Value("${app.file.path-image}")
    private String SAVE_IMAGE_PATH;

    @Value("${app.file.path-video}")
    private String SAVE_VIDEO_PATH;

    public FileEntity writeFile(MultipartFile file) {
        String filePath;
        String fileName = RandomString.make(6) + "." + ImageCompressor.getExtension(file.getContentType());
        String contentType = file.getContentType();
        if (ImageCompressor.getTypeData(file.getContentType()).equals("image")) {
            filePath = SAVE_IMAGE_PATH;
            try {
                file = ImageCompressor.compressImage(file);
            } catch (IOException e) {
                log.warn("File compressing got exception" + e.getMessage());
            }
        } else if (ImageCompressor.getTypeData(file.getContentType()).equals("video")) {
            filePath = SAVE_VIDEO_PATH;
        } else {
            throw new FileTypeException("Format error");
        }
        try {
            Files.createDirectories(Paths.get(filePath));
            filePath += "/" + fileName;
            byte[] bytes = file.getBytes();
            Path path = Paths.get(filePath);
            Files.write(path, bytes);
        } catch (IOException e) {
            throw new FileTypeException(e.getMessage());
        }
        return new FileEntity(
                filePath,
                fileName,
                contentType,
                file.getSize()
        );
    }

}
