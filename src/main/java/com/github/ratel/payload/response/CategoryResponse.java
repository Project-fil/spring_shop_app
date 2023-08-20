package com.github.ratel.payload.response;

import com.github.ratel.entity.Subcategory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryResponse {

    private Long id;

    private String name;

    private Set<SubcategoryResponse> subcategories;

    private boolean removed;

    private Date updateAt;

    private Date createAt;

    public CategoryResponse(Long id, String name, boolean removed, Date updateAt, Date createAt) {
        this.id = id;
        this.name = name;
        this.removed = removed;
        this.updateAt = updateAt;
        this.createAt = createAt;
    }
}
