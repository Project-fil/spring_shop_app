package com.github.ratel.repositories;

import com.github.ratel.entity.Product;
import com.github.ratel.entity.Subcategory;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @EntityGraph(attributePaths = {"subcategory", "comments"})
    Optional<Product>findById(long id);

    List<Product> findAllBySubcategory(Subcategory subcategory);

    List<Product> findAllByRemovedTrue();

    @Query("SELECT p FROM Product p WHERE p.id IN :idList")
    List<Product> findAllByIdIn(@Param("idList") List<Long> idList);

    Optional<Product> findByIdAndRemovedTrue(long id);

}
