package com.github.ratel.payload.response;

import lombok.*;

import java.util.Set;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponse {

    private Long id;

    private String price;

    private Integer totalAmount;

    private Set<OrderDetailsResponse> orderedProducts;

    private Long userId;

    private String note;

    private String orderStatus;

    private Boolean removed;

    private String lastModifiedDate;

    private String createdDate;

}
