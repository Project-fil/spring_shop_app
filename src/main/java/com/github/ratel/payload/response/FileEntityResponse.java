package com.github.ratel.payload.response;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class FileEntityResponse {

    private Long id;

    private String path;

    private String fileName;

    private String contentType;

    private long size;

    private boolean removed;

    private Date cratedAt;

    private Date updatedAt;

    public FileEntityResponse(Long id, String path, String fileName, String contentType, long size) {
        this.id = id;
        this.path = path;
        this.fileName = fileName;
        this.contentType = contentType;
        this.size = size;
    }
}
