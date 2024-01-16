package com.github.ratel.services.impl;

import com.github.ratel.entity.Category;
import com.github.ratel.entity.Subcategory;
import com.github.ratel.exceptions.EntityNotFoundException;
import com.github.ratel.repositories.SubcategoryRepository;
import com.github.ratel.services.SubcategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SubcategoryServiceImpl implements SubcategoryService {

    private final SubcategoryRepository subcategoryRepository;

    @Override
    public List<Subcategory> findAllByCategory(Category category, Sort sort) {
        return this.subcategoryRepository.findAllByCategoryAndRemovedFalse(category, sort);
    }

    @Override
    public List<Subcategory> findAllForAdmin(long categoryId, int limit) {
        return this.subcategoryRepository.findAllByCategoryForAdmin(categoryId, limit);
    }

    @Override
    public Subcategory findById(long id) {
        return this.subcategoryRepository.findByIdAndRemovedFalse(id)
                .orElseThrow(() -> new EntityNotFoundException("Subcategory not found"));
    }

    @Override
    public Subcategory getByIdForAdmin(long id) {
        return this.subcategoryRepository.findByIdForAdmin(id)
                .orElseThrow(() -> new EntityNotFoundException("Subcategory not found"));
    }

    @Override
    public Subcategory create(Subcategory subcategory) {
        return this.subcategoryRepository.save(subcategory);
    }

    @Override
    public Subcategory update(Subcategory subcategory) {
        return this.subcategoryRepository.save(subcategory);
    }

    @Override
    public void delete(long id) {
        this.subcategoryRepository.deleteById(id);
    }
}
