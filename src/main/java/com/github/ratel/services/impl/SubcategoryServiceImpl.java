package com.github.ratel.services.impl;

import com.github.ratel.entity.Category;
import com.github.ratel.entity.Subcategory;
import com.github.ratel.exceptions.EntityNotFoundException;
import com.github.ratel.repositories.SubcategoryRepository;
import com.github.ratel.services.SubcategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SubcategoryServiceImpl implements SubcategoryService {

    private final SubcategoryRepository subcategoryRepository;

    @Override
    public List<Subcategory> findByAll(Category category) {
        return this.subcategoryRepository.findAllByCategory(category);
    }

    @Override
    public List<Subcategory> findByAllForAdmin(Category category) {
        return this.subcategoryRepository.findAllByCategoryAndRemovedFalse(category);
    }

    @Override
    public Subcategory findById(long id) {
        return this.subcategoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Subcategory not found"));
    }

    @Override
    public Subcategory getById(long id) {
        return this.subcategoryRepository.findByIdAndRemovedFalse(id)
                .orElseThrow(() -> new EntityNotFoundException("Subcategory not found"));
    }

    @Override
    public Subcategory findByName(String name) {
        return this.subcategoryRepository.findByName(name)
                .orElseThrow(() -> new EntityNotFoundException("Subcategory not found"));
    }

    @Override
    public Subcategory getByName(String name) {
        return this.subcategoryRepository.findByNameAndRemovedFalse(name)
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
