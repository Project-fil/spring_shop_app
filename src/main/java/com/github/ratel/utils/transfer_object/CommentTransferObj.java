package com.github.ratel.utils.transfer_object;

import com.github.ratel.entity.Comment;
import com.github.ratel.payload.response.CommentResponse;
import lombok.experimental.UtilityClass;

import java.util.Objects;

@UtilityClass
public class CommentTransferObj {

    public static CommentResponse fromComment(Comment payload) {
        CommentResponse response = new CommentResponse();
        if (Objects.isNull(payload)) return response;

        response.setCommentId(payload.getId());
        response.setUserId(payload.getAuthor().getId());
        response.setUserFullName(
                String.format("%s %s", payload.getAuthor().getFirstname(), payload.getAuthor().getLastname())
        );
        response.setRole(payload.getAuthor().getRoles());
        response.setProductId(payload.getProduct().getId());
        response.setProductName(payload.getProduct().getName());
        response.setText(payload.getText());
        response.setCreateDate(payload.getCreatedDate());
        return response;
    }

}
