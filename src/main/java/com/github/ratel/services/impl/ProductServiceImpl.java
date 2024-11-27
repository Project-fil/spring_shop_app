package com.github.ratel.services.impl;

import com.github.ratel.entity.FileEntity;
import com.github.ratel.entity.Product;
import com.github.ratel.entity.Subcategory;
import com.github.ratel.exceptions.EntityNotFoundException;
import com.github.ratel.handlers.FileHandler;
import com.github.ratel.payload.request.ProductRequest;
import com.github.ratel.repositories.ProductRepository;
import com.github.ratel.services.FileService;
import com.github.ratel.services.ProductService;
import com.github.ratel.services.SubcategoryService;
import com.github.ratel.utils.transfer_object.ProductTransferObj;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final FileHandler fileHandler;
    private final FileService fileService;
    private final ProductRepository productRepository;
    private final SubcategoryService subcategoryService;

    @Override
    @Transactional(readOnly = true)
    public Page<Product> findAllInSubcategory(long subcategoryId, Pageable pageable) {
        Subcategory subcategory = this.subcategoryService.findById(subcategoryId);
        return this.productRepository.findAllBySubcategoryAndRemovedFalse(subcategory, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Product> findListForIds(List<Long> products) {
        return this.productRepository.findAllByListId(products);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Product> findAllForAdmin(Pageable pageable) {
        return this.productRepository.findAllByRemovedTrue(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Product findById(long id) {
        return this.productRepository.findByIdAndRemovedFalse(id)
                .orElseThrow(() -> new EntityNotFoundException("Product not found"));
    }

    @Override
    @Transactional(readOnly = true)
    public Product findByIdForAdmin(long id) {
        return this.productRepository.findByIdForAdmin(id)
                .orElseThrow(() -> new EntityNotFoundException("Product not found"));
    }

    @Override
    @Transactional
    public Product create(ProductRequest productRequest, List<MultipartFile> files) {
        Subcategory subcategory = this.subcategoryService.findById(productRequest.getSubcategoryId());
        Product product = ProductTransferObj.toProduct(new Product(), productRequest);
        Set<FileEntity> newFiles = new HashSet<>();
        if (Objects.nonNull(files)) {
            newFiles = files.stream()
                    .map(this.fileHandler::writeFile)
                    .map(this.fileService::create)
                    .collect(Collectors.toSet());
        }
        product.setFiles(newFiles);
        product.setSubcategory(subcategory);
        return this.productRepository.save(product);
    }

    @Override
    public Product editProduct(long id, ProductRequest productRequest) {
        Product product = this.findById(id);
        ProductTransferObj.updateProduct(product, productRequest);
        return this.productRepository.save(product);
    }

    @Override
    public Product addImageToImageList(long id, List<MultipartFile> files) {
        if (Objects.isNull(files)) {
            throw new NullPointerException("List files in addImageToImageList() is null");
        }

        Product product = this.findById(id);
        Set<FileEntity> fileSet = files.stream()
                .map(this.fileHandler::writeFile)
                .map(this.fileService::create)
                .collect(Collectors.toSet());
        product.getFiles().addAll(fileSet);
        return this.productRepository.save(product);
    }

    @Override
    public Product deleteImagesFromImageList(long productId, List<Long> imageIdsList) {
        if (Objects.isNull(imageIdsList)) {
            throw new NullPointerException("List imageIdsList in deleteImagesFromImageList() is null");
        }

        Product product = this.findById(productId);
        Set<FileEntity> files = product.getFiles();
        Set<FileEntity> filesForRemove = files.stream()
                .filter(fileEntity ->  imageIdsList.contains(fileEntity.getId()))
                .collect(Collectors.toSet());
        files.removeAll(filesForRemove);
        product.setFiles(files);
        imageIdsList.forEach(this.fileService::deleteById);
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
