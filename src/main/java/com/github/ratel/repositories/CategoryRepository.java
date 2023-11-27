package com.github.ratel.repositories;

import com.github.ratel.entity.Category;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    @EntityGraph(attributePaths = "subcategories")
    Optional<Category> findById(long id);

    List<Category> findAllByRemovedTrue();

    @EntityGraph(attributePaths = "subcategories")
    Optional<Category> findByIdAndRemovedTrue(long id);

}
