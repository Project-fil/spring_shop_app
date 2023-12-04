package com.github.ratel.payload.response;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetailsResponse {

    private Long id;

    private ProductResponse product;

    private int quantity;

    private String price;

    private boolean removed;

    private String cratedAt;

    private String updatedAt;

}
