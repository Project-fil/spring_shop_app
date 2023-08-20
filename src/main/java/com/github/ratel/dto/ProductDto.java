package com.github.ratel.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
public class ProductDto {

    private String vendorCode;

    private long categoryId;

    private String name;

    private BigDecimal price;

    private long quantity;

}
