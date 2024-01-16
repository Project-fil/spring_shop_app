package com.github.ratel.repositories;

import com.github.ratel.entity.Category;
import com.github.ratel.entity.Subcategory;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubcategoryRepository extends JpaRepository<Subcategory, Long> {

    @EntityGraph(attributePaths = "products")
    Optional<Subcategory> findByIdAndRemovedFalse(long id);

    List<Subcategory> findAllByCategoryAndRemovedFalse(Category category, Sort sort);

    @Query(
            value = "SELECT * FROM subcategories WHERE category_id=:categoryId AND is_removed=true LIMIT :limit",
            nativeQuery = true
    )
    List<Subcategory> findAllByCategoryForAdmin(
            @Param("categoryId") long categoryId,
            @Param("limit") int limit
    );

    @EntityGraph(attributePaths = "products")
    @Query("SELECT s FROM Subcategory s WHERE s.id=:id AND (s.removed=false OR s.removed=true)")
    Optional<Subcategory> findByIdForAdmin(@Param("id") long id);

}
