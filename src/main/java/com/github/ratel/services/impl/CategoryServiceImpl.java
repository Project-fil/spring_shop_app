package com.github.ratel.services.impl;

import com.github.ratel.entity.Category;
import com.github.ratel.exceptions.EntityNotFoundException;
import com.github.ratel.repositories.CategoryRepository;
import com.github.ratel.services.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public List<Category> findAllCategory(Sort sort) {
        return this.categoryRepository.findAll(sort);
    }

    @Override
    public List<Category> findAllCategoryForAdmin(int limit) {
        return this.categoryRepository.findAllForAdmin(limit);
    }

    @Override
    public Category findById(long id) {
        return this.categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Category not found"));
    }

    @Override
    public Category getByIdForAdmin(long id) {
        return this.categoryRepository.findByIdForAdmin(id)
                .orElseThrow(() -> new EntityNotFoundException("Category not found"));
    }

    @Override
    public Category createCategory(Category category) {
        return this.categoryRepository.save(category);
    }

    @Override
    public Category updateCategory(Category category) {
        return this.categoryRepository.save(category);
    }

    @Override
    public void deleteCategoryById(long id) {
        this.categoryRepository.deleteById(id);
    }

}
