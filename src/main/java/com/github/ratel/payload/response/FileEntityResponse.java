package com.github.ratel.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
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


}
