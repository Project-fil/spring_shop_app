package com.github.ratel.payload.response;

import lombok.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponse {

    private Long id;

    private String name;

    private String vendorCode;

    private String description;

    private BigDecimal price;

    private Set<FileEntityResponse> files = new HashSet<>();

    private int quantity;

    private String subcategoryId;

    private String brand;

    private Set<CommentResponse> comments;

    private Date cratedAt;

    private Date updatedAt;

    public ProductResponse(
            Long id,
            String name,
            String vendorCode,
            String description,
            BigDecimal price,
            Set<FileEntityResponse> files,
            int quantity,
            String subcategoryId,
            String brand,
            Set<CommentResponse> comments
    ) {
        this.id = id;
        this.name = name;
        this.vendorCode = vendorCode;
        this.description = description;
        this.price = price;
        this.files = files;
        this.quantity = quantity;
        this.subcategoryId = subcategoryId;
        this.brand = brand;
        this.comments = comments;
    }
}
