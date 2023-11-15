package com.github.ratel.repositories;

import com.github.ratel.entity.Product;
import com.github.ratel.entity.Subcategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findAllBySubcategory(Subcategory subcategory);

    List<Product> findAllByRemovedTrue();

    Optional<Product> findByIdAndRemovedTrue(long id);

    Optional<Product> findByVendorCode(String code);

    Optional<Product> findByVendorCodeAndRemovedTrue(String code);

}
