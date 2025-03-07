package com.github.ratel.payload.request;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CommentUpdateRequest {

    @NotNull
    private Long commentId;

    @NotBlank
    private String text;
}
