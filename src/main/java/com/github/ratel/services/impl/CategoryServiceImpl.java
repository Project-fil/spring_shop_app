package com.github.ratel.services.impl;

import com.github.ratel.entity.Category;
import com.github.ratel.exceptions.EntityNotFoundException;
import com.github.ratel.repositories.CategoryRepository;
import com.github.ratel.services.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public List<Category> findAllCategory() {
        return this.categoryRepository.findAll(); // todo research Sort functional
    }

    @Override
    public List<Category> findAllCategoryForAdmin() {
        return this.categoryRepository.findAllByRemovedFalse();
    }

    @Override
    public Category findById(long id) {
        return this.categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Category not found"));
    }

    @Override
    public Category getByIdForAdmin(long id) {
        return this.categoryRepository.findByIdAndRemovedFalse(id)
                .orElseThrow(() -> new EntityNotFoundException("Category not found"));
    }

    @Override
    public Category findCategoryByName(String name) {
        return this.categoryRepository.findByName(name)
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
