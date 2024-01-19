package com.github.ratel.payload.response;

import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class SubcategoryResponse {

    private Long id;

    private String name;

    private String categoryId;

    private List<ProductResponse> productResponseList;

    private Boolean removed;

    private String cratedAt;

    private String updatedAt;

}
