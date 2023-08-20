package com.github.ratel.utils.transfer_object;

import com.github.ratel.entity.Comment;
import com.github.ratel.payload.response.CommentResponse;
import lombok.experimental.UtilityClass;

import java.util.Objects;
import java.util.Set;

@UtilityClass
public class CommentsTransferObj {

    public static CommentResponse fromComment(Comment payload) {
        if (Objects.isNull(payload)){
            return new CommentResponse();
        }
        return new CommentResponse(

        );
    }

}
