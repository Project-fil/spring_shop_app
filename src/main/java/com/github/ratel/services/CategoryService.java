package com.github.ratel.services;

import com.github.ratel.entity.Category;

import java.util.List;

public interface CategoryService {

    List<Category> findAllCategory();

    List<Category> findAllCategoryForAdmin();

    Category findById(long id);

    Category getByIdForAdmin(long id);

    Category findCategoryByName(String name);

    Category createCategory(Category category);

    Category updateCategory(Category category);

    void deleteCategoryById(long id);
}
