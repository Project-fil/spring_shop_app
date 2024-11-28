package com.github.ratel.controllers.impl;

import com.github.ratel.controllers.ApiSecurityHeader;
import com.github.ratel.controllers.interfaces.ProductController;
import com.github.ratel.entity.Product;
import com.github.ratel.payload.request.ProductRequest;
import com.github.ratel.payload.response.MessageResponse;
import com.github.ratel.payload.response.ProductResponse;
import com.github.ratel.services.ProductService;
import com.github.ratel.utils.EntityUtil;
import com.github.ratel.utils.transfer_object.ProductTransferObj;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/app/shop/")
@RestController(value = "productControllerAdmin")
public class ProductControllerImpl implements ApiSecurityHeader, ProductController {

    private final ProductService productService;

    @Override
    @CrossOrigin("*")
    public ResponseEntity<Page<ProductResponse>> findAll(
            long subcategoryId,
            int page,
            int size,
            String sortBy,
            String sortDirection
    ) {
        PageRequest pageRequest = EntityUtil.getPageRequest(page, size, sortBy, sortDirection);
        return ResponseEntity.ok(this.productService.findAllInSubcategory(subcategoryId, pageRequest)
                .map(ProductTransferObj::fromLazyProduct));
    }

    @Override
    @CrossOrigin("*")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<Page<ProductResponse>> findAllForAdmin(
            int page,
            int size,
            String sortBy,
            String sortDirection
    ) {
        PageRequest pageRequest = EntityUtil.getPageRequest(page, size, sortBy, sortDirection);
        return ResponseEntity.ok(this.productService.findAllForAdmin(pageRequest)
                .map(ProductTransferObj::fromLazyProduct));
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
        return ResponseEntity.ok(ProductTransferObj.fromProductForAdmin(this.productService.findByIdForAdmin(id)));
    }

    @Override
    @CrossOrigin("*")
    @Secured({"ROLE_ADMIN", "ROLE_MANAGER"})
    public ResponseEntity<ProductResponse> create(ProductRequest productRequest, List<MultipartFile> files) {
        return ResponseEntity.ok(ProductTransferObj.fromProduct(this.productService.create(productRequest, files)));
    }

    @Override
    @CrossOrigin("*")
    @Secured({"ROLE_ADMIN", "ROLE_MANAGER"})
    public ResponseEntity<ProductResponse> update(long id, ProductRequest productRequest) {
        return ResponseEntity.ok(ProductTransferObj.fromProduct(this.productService.editProduct(id, productRequest)));
    }

    @Override
    @CrossOrigin("*")
    @Secured({"ROLE_ADMIN", "ROLE_MANAGER"})
    public ResponseEntity<ProductResponse> addToImageList(long productId, List<MultipartFile> files) {
        Product product = this.productService.addImageToImageList(productId, files);
        return ResponseEntity.ok(ProductTransferObj.fromLazyProduct(product));
    }

    @Override
    @CrossOrigin("*")
    @Secured({"ROLE_ADMIN", "ROLE_MANAGER"})
    public ResponseEntity<ProductResponse> deleteFromImageList(long productId, List<Long> imageIdsList) {
        Product product = this.productService.deleteImagesFromImageList(productId, imageIdsList);
        return ResponseEntity.ok(ProductTransferObj.fromLazyProduct(product));
    }

    @Override
    @CrossOrigin("*")
    @Secured({"ROLE_ADMIN", "ROLE_MANAGER"})
    public ResponseEntity<MessageResponse> delete(long id) {
        this.productService.deleteById(id);
        return ResponseEntity.ok(new MessageResponse(
                "Delete image successful",
                new Date()
        ));
    }
}
