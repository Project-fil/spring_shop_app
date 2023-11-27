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

}
