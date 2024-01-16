package com.github.ratel.payload.response;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class MessageResponse {

    private String message;

    private Date date;

}
