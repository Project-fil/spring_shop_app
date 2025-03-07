package com.github.ratel.services;

import com.github.ratel.entity.Subcategory;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface SubcategoryService {

    List<Subcategory> findAllByCategory(Long categoryId, Sort sort);

    List<Subcategory> findAllForAdmin(Long categoryId, Integer limit);

    Subcategory findById(Long id);

    Subcategory getByIdForAdmin(Long id);

    Subcategory create(Long categoryId, String name);

    Subcategory update(Long subCategoryId, String name);

    void delete(Long id);

}
