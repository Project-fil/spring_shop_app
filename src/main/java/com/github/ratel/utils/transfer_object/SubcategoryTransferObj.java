package com.github.ratel.utils.transfer_object;

import com.github.ratel.entity.Subcategory;
import com.github.ratel.payload.response.SubcategoryResponse;
import lombok.experimental.UtilityClass;

import java.util.stream.Collectors;

@UtilityClass
public class SubcategoryTransferObj {

    public static SubcategoryResponse fromSubcategory(Subcategory payload) {
       return new SubcategoryResponse(
                payload.getId(),
                payload.getName(),
                payload.getCategory().getName(),
                payload.getProducts().stream().map(ProductTransferObj::fromProduct).collect(Collectors.toList())
        );
    }

    public static SubcategoryResponse fromSubcategoryForAdmin(Subcategory payload) {
        return new SubcategoryResponse(
                payload.getId(),
                payload.getName(),
                payload.getCategory().getName(),
                payload.getProducts().stream().map(ProductTransferObj::fromProduct).collect(Collectors.toList()),
                payload.isRemoved(),
                payload.getCratedAt(),
                payload.getUpdatedAt()
        );
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
