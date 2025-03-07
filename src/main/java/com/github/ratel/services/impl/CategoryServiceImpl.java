package com.github.ratel.services.impl;

import com.github.ratel.entity.Category;
import com.github.ratel.exceptions.AppException;
import com.github.ratel.exceptions.EntityNotFoundException;
import com.github.ratel.repositories.CategoryRepository;
import com.github.ratel.services.CategoryService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Category> findAllCategory(Sort sort) {
        if (Objects.isNull(sort)) {
            throw new AppException("Invalid parameters value: sort(%)", sort);
        }
        return this.categoryRepository.findAllByRemovedFalse(sort);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Category> findAllCategoryForAdmin(int limit) {
        return this.categoryRepository.findAllForAdmin(limit);
    }

    @Override
    @Transactional(readOnly = true)
    public Category findById(Long id) {
        if (Objects.isNull(id)) {
            throw new AppException("Invalid parameters value: id(%s)", id);
        }
        return this.categoryRepository.findByIdAndRemovedFalse(id)
                .orElseThrow(() -> new EntityNotFoundException("Category not found"));
    }

    @Override
    @Transactional(readOnly = true)
    public Category getByIdForAdmin(Long id) {
        if (Objects.isNull(id)) {
            throw new AppException("Invalid parameters value: id(%s)", id);
        }
        return this.categoryRepository.findByIdForAdmin(id)
                .orElseThrow(() -> new EntityNotFoundException("Category not found"));
    }

    @Override
    @Transactional
    public Category createCategory(String name) {
        if (Objects.isNull(name) || name.isEmpty()) {
            throw new AppException("Invalid parameters value: name(%s)", name);
        }
        return this.categoryRepository.save(new Category(name));
    }

    @Override
    @Transactional
    public Category updateCategory(Long id, String name) {
        if (ObjectUtils.anyNull(id, name)) {
            throw new AppException("Invalid parameters value: id(%s), name(%s)", id, name);
        }
        Category category = this.findById(id);
        category.setName(name);
        return this.categoryRepository.save(category);
    }

    @Override
    @Transactional
    public void deleteCategoryById(Long id) {
        if (Objects.isNull(id)) {
            throw new AppException("Invalid parameters value: id(%s)", id);
        }
        this.categoryRepository.deleteById(id);
    }

}
