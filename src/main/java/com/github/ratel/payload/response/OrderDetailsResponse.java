package com.github.ratel.payload.response;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetailsResponse {

    private Long id;

    private int quantity;

    private ProductResponse product;

    private boolean removed;

    private String cratedAt;

    private String updatedAt;

}
