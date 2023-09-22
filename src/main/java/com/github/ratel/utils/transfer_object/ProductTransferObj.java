package com.github.ratel.utils.transfer_object;

import com.github.ratel.entity.Comment;
import com.github.ratel.entity.Product;
import com.github.ratel.payload.request.ProductRequest;
import com.github.ratel.payload.response.CommentResponse;
import com.github.ratel.payload.response.ProductResponse;
import lombok.experimental.UtilityClass;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@UtilityClass
public class ProductTransferObj {

    public static ProductResponse fromProduct(Product payload) {
        return new ProductResponse(
                payload.getId(),
                payload.getName(),
                payload.getVendorCode(),
                payload.getDescription(),
                payload.getPrice(),
                payload.getFiles().stream().map(FileTransferObj::fromFile).collect(Collectors.toSet()),
                payload.getQuantity(),
                payload.getSubcategory().getName(),
                payload.getBrand(),
                checkComment(payload.getComments()),
                payload.isRemoved(),
                payload.getCratedAt(),
                payload.getUpdatedAt()
        );
    }

    public static Product toProduct(Product product, ProductRequest payload) {
        product.setName(payload.getName());
        product.setVendorCode(payload.getVendorCode());
        product.setDescription(payload.getDescription());
        product.setBrand(payload.getBrand());
        product.setPrice(BigDecimal.valueOf(Double.parseDouble(payload.getPrice())));
        product.setQuantity(Integer.parseInt(payload.getQuantity()));
        return product;
    }

    private static Set<CommentResponse> checkComment(Set<Comment> comments) {
        if(Objects.isNull(comments)) {
            return Set.of();
        }
        return comments.stream().map(CommentsTransferObj::fromComment).collect(Collectors.toSet());
    }

}
