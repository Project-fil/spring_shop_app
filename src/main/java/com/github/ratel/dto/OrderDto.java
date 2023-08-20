package com.github.ratel.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto {

    private long order_item_id;

    private BigDecimal price;

    private Date createdAt;

    private String email;

    private String address;

}
