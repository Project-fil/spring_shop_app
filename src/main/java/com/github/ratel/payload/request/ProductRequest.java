package com.github.ratel.payload.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequest {

    private String name;

    private String vendorCode;

    private String description;

    private BigDecimal price;

    private String brand;

    private int quantity;

    private long subcategoryId;

}
