package com.github.ratel.repositories;

import com.github.ratel.entity.Category;
import com.github.ratel.entity.Subcategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SubcategoryRepository extends JpaRepository<Subcategory, Long> {

    List<Subcategory> findAllByCategory(Category category);

    List<Subcategory> findAllByCategoryAndRemovedFalse(Category category);

    Optional<Subcategory> findByIdAndRemovedFalse(long id);

    Optional<Subcategory> findByName(String name);

    Optional<Subcategory> findByNameAndRemovedFalse(String name);

}
