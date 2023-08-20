package com.github.ratel.services;

import com.github.ratel.entity.Product;
import com.github.ratel.entity.Subcategory;

import java.util.List;

public interface ProductService {

    List<Product> findAll(Subcategory subcategory);

    List<Product> findAllForAdmin();

    Product findById(long id);

    Product findByIdForAdmin(long id);

    Product findByVendorCode(String code);

    Product findByVendorCodeForAdmin(String code);

    Product create(Product product);

    Product update(Product product);

    void deleteById(long id);
}
