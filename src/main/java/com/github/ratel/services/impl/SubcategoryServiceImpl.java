package com.github.ratel.services.impl;

import com.github.ratel.entity.Category;
import com.github.ratel.entity.Subcategory;
import com.github.ratel.exceptions.AppException;
import com.github.ratel.exceptions.EntityNotFoundException;
import com.github.ratel.repositories.SubcategoryRepository;
import com.github.ratel.services.CategoryService;
import com.github.ratel.services.SubcategoryService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class SubcategoryServiceImpl implements SubcategoryService {

    private final CategoryService categoryService;
    private final SubcategoryRepository subcategoryRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Subcategory> findAllByCategory(Long categoryId, Sort sort) {
        if (ObjectUtils.anyNull(categoryId, sort)) {
            throw new AppException("Invalid parameters value: categoryId(%s), sort(%s)", categoryId, sort);
        }
        Category category = this.categoryService.findById(categoryId);
        return this.subcategoryRepository.findAllByCategoryAndRemovedFalse(category, sort);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Subcategory> findAllForAdmin(Long categoryId, Integer limit) {
        if (ObjectUtils.anyNull(categoryId, limit)) {
            throw new AppException("Invalid parameters value: categoryId(%s), limit(%s)", categoryId, limit);
        }
        return this.subcategoryRepository.findAllByCategoryForAdmin(categoryId, limit);
    }

    @Override
    @Transactional(readOnly = true)
    public Subcategory findById(Long id) {
        if (Objects.isNull(id)) {
            throw new AppException("Invalid parameters value: id(%s)", id);
        }
        return this.subcategoryRepository.findByIdAndRemovedFalse(id)
                .orElseThrow(() -> new EntityNotFoundException("Subcategory not found"));
    }

    @Override
    @Transactional(readOnly = true)
    public Subcategory getByIdForAdmin(Long id) {
        if (Objects.isNull(id)) {
            throw new AppException("Invalid parameters value: id(%s)", id);
        }
        return this.subcategoryRepository.findByIdForAdmin(id)
                .orElseThrow(() -> new EntityNotFoundException("Subcategory not found"));
    }

    @Override
    @Transactional
    public Subcategory create(Long categoryId, String name) {
        if (ObjectUtils.anyNull(categoryId, name)) {
            throw new AppException("Invalid parameters value: categoryId(%s), name(%s)", categoryId, name);
        }
        Category category = this.categoryService.findById(categoryId);
        Subcategory subcategory = new Subcategory();
        subcategory.setName(name);
        subcategory.setCategory(category);
        return this.subcategoryRepository.save(subcategory);
    }

    @Override
    @Transactional
    public Subcategory update(Long subCategoryId, String name) {
        if (ObjectUtils.anyNull(subCategoryId, name)) {
            throw new AppException("Invalid parameters value: subCategoryId(%s), name(%s)", subCategoryId, name);
        }
        Subcategory subcategory = this.findById(subCategoryId);
        subcategory.setName(name);
        return this.subcategoryRepository.save(subcategory);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (Objects.isNull(id)) {
            throw new AppException("Invalid parameters value: id(%s)", id);
        }
        this.subcategoryRepository.deleteById(id);
    }

}
