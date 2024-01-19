package com.github.ratel.utils.transfer_object;

import com.github.ratel.entity.Category;
import com.github.ratel.payload.response.CategoryResponse;
import lombok.experimental.UtilityClass;

import java.util.stream.Collectors;

@UtilityClass
public class CategoryTransferObj {

    public static CategoryResponse fromLazyCategory(Category payload) {
        CategoryResponse response = new CategoryResponse();
        response.setId(payload.getId());
        response.setName(payload.getName());
        return response;
    }

    public static CategoryResponse fromCategory(Category payload) {
        CategoryResponse response = new CategoryResponse();
        response.setId(payload.getId());
        response.setName(payload.getName());
        response.setSubcategories(
                payload.getSubcategories().stream()
                        .map(SubcategoryTransferObj::fromLazySubcategory)
                        .collect(Collectors.toSet())
        );
        return response;
    }

    public static CategoryResponse fromCategoryForAdmin(Category payload) {
        CategoryResponse response = new CategoryResponse();
        response.setId(payload.getId());
        response.setName(payload.getName());
        response.setSubcategories(
                payload.getSubcategories().stream()
                        .map(SubcategoryTransferObj::fromSubcategory)
                        .collect(Collectors.toSet())
        );
        response.setRemoved(payload.isRemoved());
        response.setUpdateAt(payload.getUpdatedAt().toString());
        response.setCreateAt(payload.getCratedAt().toString());
        return response;
    }

}
