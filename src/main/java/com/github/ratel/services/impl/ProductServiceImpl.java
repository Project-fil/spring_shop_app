package com.github.ratel.services.impl;

import com.github.ratel.entity.Product;
import com.github.ratel.entity.Subcategory;
import com.github.ratel.exceptions.EntityNotFoundException;
import com.github.ratel.repositories.ProductRepository;
import com.github.ratel.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public List<Product> findAllInSubcategory(Subcategory subcategory) {
        return this.productRepository.findAllBySubcategoryAndRemovedFalse(subcategory);
    }

    @Override
    public List<Product> findListForIds(List<Long> products) {
        return this.productRepository.findAllByListId(products);
    }

    @Override
    public List<Product> findAllForAdmin() {
        return this.productRepository.findAllForAdmin();
    }

    @Override
    public Product findById(long id) {
        return this.productRepository.findByIdAndRemovedFalse(id)
                .orElseThrow(() -> new EntityNotFoundException("Product not found"));
    }

    @Override
    public Product findByIdForAdmin(long id) {
        return this.productRepository.findByIdForAdmin(id).orElseThrow(
                () -> new EntityNotFoundException("Product not found")
        );
    }

    @Override
    public Product create(Product product) {
        return this.productRepository.save(product);
    }

    @Override
    public Product update(Product product) {
        return this.productRepository.save(product);
    }

    @Override
    public void deleteById(long id) {
        this.productRepository.deleteById(id);
    }
}
