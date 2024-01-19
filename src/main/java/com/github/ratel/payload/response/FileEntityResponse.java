package com.github.ratel.payload.response;

import lombok.*;

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

    private Boolean removed;

    private String cratedAt;

    private String updatedAt;

}
