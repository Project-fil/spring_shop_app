package com.github.ratel.services.impl;

import com.github.ratel.entity.Product;
import com.github.ratel.entity.Subcategory;
import com.github.ratel.exceptions.EntityNotFoundException;
import com.github.ratel.repositories.ProductRepository;
import com.github.ratel.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public List<Product> findAllInSubcategory(Subcategory subcategory) {
        return this.productRepository.findAllBySubcategory(subcategory);
    }

    @Override
    public List<Product> findListForIds(List<Long> products) {
        return this.productRepository.findAllByIdIn(products);
    }

    @Override
    public List<Product> findAllForAdmin() {
        return this.productRepository.findAllByRemovedTrue();
    }

    @Override
    public Product findById(long id) {
        return this.productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product not found"));
    }

    @Override
    public Product findByIdForAdmin(long id) {
        return this.productRepository.findByIdAndRemovedTrue(id).orElseThrow(
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
