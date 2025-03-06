package com.github.ratel.payload.response;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class OrderStatisticResponse {

    private Long id;

    private Long userId;

    private String totalAmount;

    private Integer total;

    private String note;

    private String orderStatus;

    private String lastModifiedDate;

    private String createdDate;

}
