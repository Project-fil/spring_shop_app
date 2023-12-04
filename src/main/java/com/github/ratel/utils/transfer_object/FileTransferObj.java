package com.github.ratel.utils.transfer_object;

import com.github.ratel.entity.FileEntity;
import com.github.ratel.payload.response.FileEntityResponse;
import lombok.experimental.UtilityClass;

@UtilityClass
public class FileTransferObj {

    public static FileEntityResponse fromFile(FileEntity fileEntity) {
        if (fileEntity == null) {
            return null;
        }
        FileEntityResponse response = new FileEntityResponse();
        response.setId(fileEntity.getId());
        response.setPath(fileEntity.getPath());
        response.setFileName(fileEntity.getFileName());
        response.setContentType(fileEntity.getContentType());
        response.setSize(fileEntity.getSize());
        return response;
    }

    public static FileEntityResponse fromFileForAdmin(FileEntity fileEntity) {
        if (fileEntity == null) {
            return null;
        }
        FileEntityResponse response = new FileEntityResponse();
        response.setId(fileEntity.getId());
        response.setPath(fileEntity.getPath());
        response.setFileName(fileEntity.getFileName());
        response.setContentType(fileEntity.getContentType());
        response.setSize(fileEntity.getSize());
        response.setRemoved(fileEntity.isRemoved());
        response.setCratedAt(fileEntity.getCratedAt().toString());
        response.setUpdatedAt(fileEntity.getUpdatedAt().toString());
        return response;
    }

}
