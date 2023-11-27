package com.github.ratel.services;

import com.github.ratel.entity.Product;
import com.github.ratel.entity.Subcategory;

import java.util.List;
import java.util.Set;

public interface ProductService {

    List<Product> findAllInSubcategory(Subcategory subcategory);

    List<Product> findListForIds(List<Long> products);

    List<Product> findAllForAdmin();

    Product findById(long id);

    Product findByIdForAdmin(long id);

    Product create(Product product);

    Product update(Product product);

    void deleteById(long id);
}
