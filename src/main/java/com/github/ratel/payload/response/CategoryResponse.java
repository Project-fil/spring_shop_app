package com.github.ratel.payload.response;

import lombok.*;

import java.util.Date;
import java.util.Set;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CategoryResponse {

    private Long id;

    private String name;

    private Set<SubcategoryResponse> subcategories;

    private boolean removed;

    private Date updateAt;

    private Date createAt;

}
