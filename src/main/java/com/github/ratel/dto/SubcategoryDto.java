package com.github.ratel.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubcategoryDto {

    private long id;

    @NotBlank
    private String name;

//    private List<ProductDto> products = new ArrayList<>();

}
