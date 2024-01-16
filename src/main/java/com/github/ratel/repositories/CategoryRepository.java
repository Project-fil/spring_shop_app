package com.github.ratel.repositories;

import com.github.ratel.entity.Category;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    @EntityGraph(attributePaths = "subcategories")
    Optional<Category> findById(long id);

    @Query(value = "SELECT * FROM categories WHERE is_removed=true LIMIT :limit", nativeQuery = true)
    List<Category> findAllForAdmin(@Param("limit") int limit);

    @EntityGraph(attributePaths = "subcategories")
    @Query("SELECT c FROM Category c WHERE c.id=:id AND (c.removed=false OR c.removed=true)")
    Optional<Category> findByIdForAdmin(@Param("id") long id);

}
