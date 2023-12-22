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

    private String totalAmount;

    private Integer total;

    private Set<OrderDetailsResponse> orderedProducts;

    private UserResponse userResponse;

    private String note;

    private String orderStatus;

    private Boolean removed;

    private String lastModifiedDate;

    private String createdDate;

}
