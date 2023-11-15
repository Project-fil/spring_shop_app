package com.github.ratel.payload.dto;

import com.github.ratel.entity.Product;
import lombok.*;

import java.util.Map;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CartDto {

    private Long id;

    private Map<Product, Integer> products;

}
