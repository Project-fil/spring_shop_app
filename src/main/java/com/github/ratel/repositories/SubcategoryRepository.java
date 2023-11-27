package com.github.ratel.repositories;

import com.github.ratel.entity.Category;
import com.github.ratel.entity.Subcategory;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

@Repository
public interface SubcategoryRepository extends JpaRepository<Subcategory, Long> {

    @EntityGraph(attributePaths = "products")
    Optional<Subcategory>findById(long id);

    List<Subcategory> findAllByCategory(Category category);

    List<Subcategory> findAllByCategoryAndRemovedTrue(Category category);

    @EntityGraph(attributePaths = "products")
    Optional<Subcategory> findByIdAndRemovedTrue(long id);

}
