package com.github.ratel.payload.response;

import lombok.*;

import java.math.BigDecimal;
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

    private String cratedAt;

    private String updatedAt;

}
