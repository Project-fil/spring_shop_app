package com.github.ratel.controllers.impl;

import com.github.ratel.controllers.ApiSecurityHeader;
import com.github.ratel.controllers.interfaces.ProductController;
import com.github.ratel.entity.FileEntity;
import com.github.ratel.entity.Product;
import com.github.ratel.entity.Subcategory;
import com.github.ratel.handlers.FileHandler;
import com.github.ratel.payload.request.ProductRequest;
import com.github.ratel.payload.response.MessageResponse;
import com.github.ratel.payload.response.ProductResponse;
import com.github.ratel.services.FileService;
import com.github.ratel.services.ProductService;
import com.github.ratel.services.SubcategoryService;
import com.github.ratel.utils.transfer_object.ProductTransferObj;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.File;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@CrossOrigin("*")
@RequiredArgsConstructor
@RequestMapping("/app/shop/")
@RestController(value = "productControllerAdmin")
public class ProductControllerImpl implements ApiSecurityHeader, ProductController {

    private final FileHandler fileHandler;

    private final FileService fileService;

    private final ProductService productService;

    private final SubcategoryService subcategoryService;

    @Override
    @CrossOrigin("*")
    public ResponseEntity<List<ProductResponse>> findAll(long subcategoryId) {
        Subcategory subcategory = this.subcategoryService.findById(subcategoryId);
        return ResponseEntity.ok(
                this.productService.findAllInSubcategory(subcategory).stream()
                        .map(ProductTransferObj::fromLazyProduct)
                        .collect(Collectors.toList())
        );
    }

    @Override
    @CrossOrigin("*")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<List<ProductResponse>> findAllForAdmin() {
        return ResponseEntity.ok(
                this.productService.findAllForAdmin().stream()
                        .map(ProductTransferObj::fromLazyProduct)
                        .collect(Collectors.toList())
        );
    }

    @Override
    @CrossOrigin("*")
    public ResponseEntity<ProductResponse> findById(long id) {
        return ResponseEntity.ok(ProductTransferObj.fromProduct(this.productService.findById(id)));
    }

    @Override
    @CrossOrigin("*")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<ProductResponse> findByIdForAdmin(long id) {
        Product getProduct = null;
        try {
            getProduct = this.productService.findById(id);
        } catch (Exception ignore) {
        }
        if (Objects.isNull(getProduct)) {
            getProduct = this.productService.findByIdForAdmin(id);
        }
        return ResponseEntity.ok(ProductTransferObj.fromProductForAdmin(getProduct));
    }

    @Override
    @Transactional
    @CrossOrigin("*")
    @Secured({"ROLE_ADMIN", "ROLE_MANAGER"})
    public ResponseEntity<ProductResponse> create(ProductRequest productRequest, List<MultipartFile> files) {
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
        return ResponseEntity.ok(ProductTransferObj.fromProduct(this.productService.create(product)));
    }

    @Override
    @Transactional
    @CrossOrigin("*")
    @Secured({"ROLE_ADMIN", "ROLE_MANAGER"})
    public ResponseEntity<ProductResponse> update(long id, ProductRequest productRequest) {
        Product product = this.productService.findById(id);
        product = ProductTransferObj.updateProduct(product, productRequest);
        return ResponseEntity.ok(ProductTransferObj.fromProduct(this.productService.update(product)));
    }

    @Override
    @Transactional
    @CrossOrigin("*")
    @Secured({"ROLE_ADMIN", "ROLE_MANAGER"})
    public ResponseEntity<ProductResponse> addToImageList(Long id, List<MultipartFile> files) {
        Product product = this.productService.findById(id);
        if (Objects.nonNull(files)) {
            Set<FileEntity> filesList = files.stream()
                    .map(this.fileHandler::writeFile)
                    .map(this.fileService::create)
                    .collect(Collectors.toSet());
            product.getFiles().addAll(filesList);
            this.productService.update(product);
        }
        return ResponseEntity.ok(ProductTransferObj.fromLazyProduct(product));
    }

    @Override
    @Transactional
    @CrossOrigin("*")
    @Secured({"ROLE_ADMIN", "ROLE_MANAGER"})
    public ResponseEntity<ProductResponse> deleteFromImageList(long productId,  List<Long> imageIdsList) {
        Product product = this.productService.findById(productId);
        if (Objects.nonNull(imageIdsList)) {
            Set<FileEntity> files = product.getFiles();
            Set<FileEntity> filesForRemove = files.stream()
                    .filter(fileEntity -> imageIdsList.contains(fileEntity.getId()))
                    .collect(Collectors.toSet());
            files.removeAll(filesForRemove);
            product.setFiles(files);
            imageIdsList.forEach(this.fileService::deleteById);
            this.productService.update(product);
        }
        return ResponseEntity.ok(ProductTransferObj.fromLazyProduct(product));
    }

    @Override
    @Transactional
    @CrossOrigin("*")
    @Secured({"ROLE_ADMIN", "ROLE_MANAGER"})
    public ResponseEntity<MessageResponse> delete(long id) {
        this.productService.deleteById(id);
        return ResponseEntity.ok(new MessageResponse("Delete image successful"));
    }
}
