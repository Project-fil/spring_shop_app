package com.github.ratel.payload.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentDto {

    private Long userId;

    private long productId;

    private String commentText;

    private Date createdAt;
}
