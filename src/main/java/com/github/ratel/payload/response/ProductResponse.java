package com.github.ratel.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
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

    private String subcategoryName;

    private String brand;

    private Set<CommentResponse> comments;

    private Date cratedAt;

    private Date updatedAt;

}
