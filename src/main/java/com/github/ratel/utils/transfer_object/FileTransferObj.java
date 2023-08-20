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
        return new FileEntityResponse(
                fileEntity.getId(),
                fileEntity.getPath(),
                fileEntity.getFileName(),
                fileEntity.getContentType(),
                fileEntity.getSize(),
                fileEntity.isRemoved(),
                fileEntity.getCratedAt(),
                fileEntity.getUpdatedAt()
        );
    }

}
