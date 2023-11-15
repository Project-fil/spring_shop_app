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

    private final ProductService productService;

    private final SubcategoryService subcategoryService;

    @Override
    @CrossOrigin("*")
    public ResponseEntity<List<ProductResponse>> findAll(long subcategoryId) {
        Subcategory subcategory = this.subcategoryService.findById(subcategoryId);
        return ResponseEntity.ok(
                this.productService.findAll(subcategory).stream()
                        .map(ProductTransferObj::fromProduct)
                        .collect(Collectors.toList())
        );
    }

    @Override
    @CrossOrigin("*")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<List<ProductResponse>> findAllForAdmin() {
        return ResponseEntity.ok(
                this.productService.findAllForAdmin().stream()
                .map(ProductTransferObj::fromProductForAdmin)
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
        }catch (Exception ignore) {}
        if (Objects.isNull(getProduct)) {
            getProduct = this.productService.findByIdForAdmin(id);
        }
        return ResponseEntity.ok(ProductTransferObj.fromProductForAdmin(getProduct));
    }

    @Override
    public ResponseEntity<ProductResponse> findByVendorCode(String code) {
        return ResponseEntity.ok(ProductTransferObj.fromProduct(this.productService.findByVendorCode(code)));
    }

    @Override
    @CrossOrigin("*")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<ProductResponse> findByVendorCodeForAdmin(String code) {
        Product getProduct = null;
        try {
            getProduct = this.productService.findByVendorCode(code);
        }catch (Exception ignore) {}
        if (Objects.isNull(getProduct)) {
            getProduct = this.productService.findByVendorCodeForAdmin(code);
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
            newFiles = files.stream().map(this.fileHandler::writeFile).collect(Collectors.toSet());
        }
        product.setFiles(newFiles);
        product.setSubcategory(subcategory);
        product = this.productService.create(product);
        return ResponseEntity.ok(ProductTransferObj.fromProduct(product));
    }

    @Override
    @CrossOrigin("*")
    @Secured({"ROLE_ADMIN", "ROLE_MANAGER"})
    public ResponseEntity<ProductResponse> update(ProductRequest productRequest) {

        return ResponseEntity.ok(ProductTransferObj.fromProduct(null));
    }

    @Override
    @CrossOrigin("*")
    @Secured({"ROLE_ADMIN", "ROLE_MANAGER"})
    public ResponseEntity<ProductResponse> updateImage(long id, MultipartFile file) {
        return null;
    }

    @Override
    @CrossOrigin("*")
    @Secured({"ROLE_ADMIN", "ROLE_MANAGER"})
    public ResponseEntity<MessageResponse> delete(long id) {
        return null;
    }
}
