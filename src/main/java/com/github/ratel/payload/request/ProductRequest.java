package com.github.ratel.payload.request;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequest {

    private String name;

    private String vendorCode;

    private String description;

    private String price;

    private String brand;

    private String quantity;

    private long subcategoryId;

}
