package com.github.ratel.repositories;

import com.github.ratel.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    Optional<Category> findByName(String name);

    List<Category> findAllByRemovedFalse();

    Optional<Category> findByIdAndRemovedFalse(long id);

}
