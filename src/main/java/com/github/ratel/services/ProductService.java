package com.github.ratel.services;

import com.github.ratel.entity.Product;
import com.github.ratel.entity.Subcategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {

    Page<Product> findAllInSubcategory(Subcategory subcategory, Pageable pageable);

    List<Product> findListForIds(List<Long> products);

    Page<Product> findAllForAdmin(Pageable pageable);

    Product findById(long id);

    Product findByIdForAdmin(long id);

    Product create(Product product);

    Product update(Product product);

    void deleteById(long id);
}
