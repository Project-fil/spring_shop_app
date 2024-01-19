package com.github.ratel.repositories;

import com.github.ratel.entity.Product;
import com.github.ratel.entity.Subcategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    Optional<Product>findByIdAndRemovedFalse(long id);

    Page<Product> findAllBySubcategoryAndRemovedFalse(Subcategory subcategory, Pageable pageable);

    @Query("SELECT p FROM Product p WHERE p.id IN :idList AND p.removed=false")
    List<Product> findAllByListId(@Param("idList") List<Long> idList);

    Page<Product> findAllByRemovedTrue(Pageable pageable);

    @EntityGraph(attributePaths = {"subcategory", "comments"})
    @Query("SELECT p FROM Product p WHERE p.id=:id AND (p.removed=false OR p.removed=true)")
    Optional<Product> findByIdForAdmin(@Param("id") long id);

}
