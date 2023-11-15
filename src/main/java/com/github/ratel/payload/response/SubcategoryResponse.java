package com.github.ratel.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubcategoryResponse {

    private Long id;

    private String name;

    private String categoryName;

    private List<ProductResponse> productResponseList;

    private boolean removed;

    private Date cratedAt;

    private Date updatedAt;

    public SubcategoryResponse(
            Long id,
            String name,
            String categoryName,
            List<ProductResponse> productResponseList
    ) {
        this.id = id;
        this.name = name;
        this.categoryName = categoryName;
        this.productResponseList = productResponseList;
    }

}
