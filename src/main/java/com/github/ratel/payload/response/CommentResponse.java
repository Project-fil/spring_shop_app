package com.github.ratel.payload.response;

import com.github.ratel.entity.enums.Roles;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentResponse {

    private Long commentId;

    private Long userId;

    private String userFullName;

    private Roles role;

    private Long productId;

    private String productName;

    private String text;

    private Date createDate;

}