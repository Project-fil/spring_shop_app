package com.github.ratel.utils.transfer_object;

import com.github.ratel.entity.Category;
import com.github.ratel.payload.response.CategoryResponse;
import lombok.experimental.UtilityClass;

import java.util.stream.Collectors;

@UtilityClass
public class CategoryTransferObj {

    public static CategoryResponse fromCategory(Category payload) {
        return new CategoryResponse(
                payload.getId(),
                payload.getName(),
                payload.getSubcategories().stream()
                        .map(SubcategoryTransferObj::fromSubcategory)
                        .collect(Collectors.toSet())
        );
    }

    public static CategoryResponse fromCategoryForAdmin(Category payload) {
        return new CategoryResponse(
                payload.getId(),
                payload.getName(),
                payload.getSubcategories().stream()
                        .map(SubcategoryTransferObj::fromSubcategoryForAdmin)
                        .collect(Collectors.toSet()),
                payload.isRemoved(),
                payload.getCratedAt(),
                payload.getCratedAt()
        );
    }

}
