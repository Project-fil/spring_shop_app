package com.github.ratel.services.impl;

import com.github.ratel.entity.Category;
import com.github.ratel.entity.Subcategory;
import com.github.ratel.exceptions.EntityNotFoundException;
import com.github.ratel.repositories.SubcategoryRepository;
import com.github.ratel.services.CategoryService;
import com.github.ratel.services.SubcategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SubcategoryServiceImpl implements SubcategoryService {

    private final CategoryService categoryService;
    private final SubcategoryRepository subcategoryRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Subcategory> findAllByCategory(long categoryId, Sort sort) {
        Category category = this.categoryService.findById(categoryId);
        return this.subcategoryRepository.findAllByCategoryAndRemovedFalse(category, sort);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Subcategory> findAllForAdmin(long categoryId, int limit) {
        return this.subcategoryRepository.findAllByCategoryForAdmin(categoryId, limit);
    }

    @Override
    @Transactional(readOnly = true)
    public Subcategory findById(long id) {
        return this.subcategoryRepository.findByIdAndRemovedFalse(id)
                .orElseThrow(() -> new EntityNotFoundException("Subcategory not found"));
    }

    @Override
    @Transactional(readOnly = true)
    public Subcategory getByIdForAdmin(long id) {
        return this.subcategoryRepository.findByIdForAdmin(id)
                .orElseThrow(() -> new EntityNotFoundException("Subcategory not found"));
    }

    @Override
    @Transactional
    public Subcategory create(long categoryId, String name) {
        Category category = this.categoryService.findById(categoryId);
        Subcategory subcategory = new Subcategory();
        subcategory.setName(name);
        subcategory.setCategory(category);
        return this.subcategoryRepository.save(subcategory);
    }

    @Override
    @Transactional
    public Subcategory update(long subCategoryId, String name) {
        Subcategory subcategory = this.findById(subCategoryId);
        subcategory.setName(name);
        return this.subcategoryRepository.save(subcategory);
    }

    @Override
    @Transactional
    public void delete(long id) {
        this.subcategoryRepository.deleteById(id);
    }
}
