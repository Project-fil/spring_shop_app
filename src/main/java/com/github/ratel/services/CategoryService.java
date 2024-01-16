package com.github.ratel.services;

import com.github.ratel.entity.Category;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface CategoryService {

    List<Category> findAllCategory(Sort sort);

    List<Category> findAllCategoryForAdmin(int limit);

    Category findById(long id);

    Category getByIdForAdmin(long id);

    Category createCategory(Category category);

    Category updateCategory(Category category);

    void deleteCategoryById(long id);
}
