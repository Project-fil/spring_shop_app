package com.github.ratel.services;

import com.github.ratel.entity.Category;
import com.github.ratel.entity.Subcategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface SubcategoryService {

    List<Subcategory> findAllByCategory(long categoryId, Sort sort);

    List<Subcategory> findAllForAdmin(long categoryId, int limit);

    Subcategory findById(long id);

    Subcategory getByIdForAdmin(long id);

    Subcategory create(long categoryId, String name);

    Subcategory update(long subCategoryId, String name);

    void delete(long id);
}
