package com.github.ratel.utils.transfer_object;

import com.github.ratel.entity.Comment;
import com.github.ratel.entity.Product;
import com.github.ratel.payload.request.ProductRequest;
import com.github.ratel.payload.response.CommentResponse;
import com.github.ratel.payload.response.ProductResponse;
import com.github.ratel.utils.EntityUtil;
import lombok.experimental.UtilityClass;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@UtilityClass
public class ProductTransferObj {

    public static ProductResponse fromLazyProduct(Product payload) {
        ProductResponse response = new ProductResponse();
        response.setId(payload.getId());
        response.setName(payload.getName());
        response.setVendorCode(payload.getVendorCode());
        response.setDescription(payload.getDescription());
        response.setPrice(payload.getPrice());
        response.setFiles(
                payload.getFiles().stream()
                        .map(FileTransferObj::fromFile)
                        .collect(Collectors.toSet())
        );
        response.setQuantity(payload.getQuantity());
        response.setBrand(payload.getBrand());
        return response;
    }

    public static ProductResponse fromProduct(Product payload) {
        ProductResponse response = new ProductResponse();
        response.setId(payload.getId());
        response.setName(payload.getName());
        response.setVendorCode(payload.getVendorCode());
        response.setDescription(payload.getDescription());
        response.setPrice(payload.getPrice());
        response.setFiles(
                payload.getFiles().stream()
                        .map(FileTransferObj::fromFile)
                        .collect(Collectors.toSet())
        );
        response.setQuantity(payload.getQuantity());
        response.setSubcategoryId(payload.getSubcategory().getId().toString());
        response.setBrand(payload.getBrand());
        response.setComments(checkComment(payload.getComments()));
        return response;
    }

    public static ProductResponse fromProductForAdmin(Product payload) {
        ProductResponse response = new ProductResponse();
        response.setId(payload.getId());
        response.setName(payload.getName());
        response.setVendorCode(payload.getVendorCode());
        response.setDescription(payload.getDescription());
        response.setPrice(payload.getPrice());
        response.setFiles(
                payload.getFiles().stream()
                        .map(FileTransferObj::fromFile)
                        .collect(Collectors.toSet())
        );
        response.setQuantity(payload.getQuantity());
        response.setSubcategoryId(payload.getSubcategory().getId().toString());
        response.setBrand(payload.getBrand());
        response.setComments(checkComment(payload.getComments()));
        response.setCratedAt(payload.getCratedAt().toString());
        response.setUpdatedAt(payload.getUpdatedAt().toString());
        return response;
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

    private static List<CommentResponse> checkComment(List<Comment> comments) {
        if(Objects.isNull(comments)) {
            return List.of();
        }
        return comments.stream().map(CommentTransferObj::fromComment).collect(Collectors.toList());
    }

    public static Product updateProduct(Product product, ProductRequest payload) {
        product.setName(EntityUtil.updateField(product.getName(), payload.getName()));
        product.setVendorCode(EntityUtil.updateField(product.getVendorCode(), payload.getVendorCode()));
        product.setDescription(EntityUtil.updateField(product.getDescription(), payload.getDescription()));
        product.setPrice(EntityUtil.updateField(
                product.getPrice(),
                EntityUtil.convertToEntityType(product.getPrice(), payload.getPrice()))
        );
        product.setBrand(EntityUtil.updateField(product.getBrand(), payload.getBrand()));
        product.setQuantity(EntityUtil.updateField(
                product.getQuantity(),
                EntityUtil.convertToEntityType(product.getQuantity(), payload.getQuantity())
        ));
        return product;
    }

}
