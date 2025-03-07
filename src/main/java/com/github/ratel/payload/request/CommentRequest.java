package com.github.ratel.payload.request;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CommentRequest {

    @NotNull
    private Long userId;

    @NotNull
    private Long productId;

    @NotBlank
    private String text;

}
