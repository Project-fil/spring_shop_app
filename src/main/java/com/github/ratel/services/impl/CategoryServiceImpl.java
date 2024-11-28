package com.github.ratel.services.impl;

import com.github.ratel.entity.Category;
import com.github.ratel.exceptions.EntityNotFoundException;
import com.github.ratel.repositories.CategoryRepository;
import com.github.ratel.services.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Category> findAllCategory(Sort sort) {
        return this.categoryRepository.findAllByRemovedFalse(sort);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Category> findAllCategoryForAdmin(int limit) {
        return this.categoryRepository.findAllForAdmin(limit);
    }

    @Override
    @Transactional(readOnly = true)
    public Category findById(long id) {
        return this.categoryRepository.findByIdAndRemovedFalse(id)
                .orElseThrow(() -> new EntityNotFoundException("Category not found"));
    }

    @Override
    @Transactional(readOnly = true)
    public Category getByIdForAdmin(long id) {
        return this.categoryRepository.findByIdForAdmin(id)
                .orElseThrow(() -> new EntityNotFoundException("Category not found"));
    }

    @Override
    @Transactional
    public Category createCategory(String name) {
        return this.categoryRepository.save(new Category(name));
    }

    @Override
    @Transactional
    public Category updateCategory(long id, String name) {
        Category category = this.findById(id);
        category.setName(name);
        return this.categoryRepository.save(category);
    }

    @Override
    @Transactional
    public void deleteCategoryById(long id) {
        this.categoryRepository.deleteById(id);
    }

}
