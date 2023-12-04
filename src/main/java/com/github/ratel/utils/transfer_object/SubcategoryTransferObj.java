package com.github.ratel.utils.transfer_object;

import com.github.ratel.entity.Subcategory;
import com.github.ratel.payload.response.SubcategoryResponse;
import lombok.experimental.UtilityClass;

import java.util.stream.Collectors;

@UtilityClass
public class SubcategoryTransferObj {

    public static SubcategoryResponse fromLazySubcategory(Subcategory payload) {
        SubcategoryResponse response = new SubcategoryResponse();
        response.setId(payload.getId());
        response.setName(payload.getName());
        return response;
    }

    public static SubcategoryResponse fromSubcategory(Subcategory payload) {
        SubcategoryResponse response = new SubcategoryResponse();
        response.setId(payload.getId());
        response.setName(payload.getName());
        response.setCategoryId(payload.getCategory().getId().toString());
        response.setProductResponseList(
                payload.getProducts().stream()
                        .map(ProductTransferObj::fromProduct)
                        .collect(Collectors.toList())
        );
        return response;
    }

    public static SubcategoryResponse fromSubcategoryForAdmin(Subcategory payload) {
        SubcategoryResponse response = new SubcategoryResponse();
        response.setId(payload.getId());
        response.setName(payload.getName());
        response.setCategoryId(payload.getCategory().getId().toString());
        response.setProductResponseList(
                payload.getProducts().stream()
                        .map(ProductTransferObj::fromProduct)
                        .collect(Collectors.toList())
        );
        response.setRemoved(payload.isRemoved());
        response.setCratedAt(payload.getCratedAt().toString());
        response.setUpdatedAt(payload.getUpdatedAt().toString());
        return response;
    }


//    public static SubcategoryResponse fromSubcategoryWithoutCategory(Subcategory payload) {
//        return new SubcategoryResponse(
//                payload.getId(),
//                payload.getName(),
//                payload.getProducts().stream().map(ProductTransferObject::fromProduct).collect(Collectors.toList()),
//                payload.isRemoved(),
//                payload.getCratedAt(),
//                payload.getUpdatedAt()
//        );
//    }

}
