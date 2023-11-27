package com.github.ratel.controllers.interfaces;

import com.github.ratel.payload.request.ProductRequest;
import com.github.ratel.payload.response.MessageResponse;
import com.github.ratel.payload.response.ProductResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@SecurityRequirement(name = "Authorization")
public interface ProductController {

    @GetMapping("free/product/find/all/{subcategoryId}")
    ResponseEntity<List<ProductResponse>> findAll(@PathVariable long subcategoryId);

    @GetMapping("product/find/all/admin")
    ResponseEntity<List<ProductResponse>> findAllForAdmin();

    @GetMapping("free/product/find/id/{id}")
    ResponseEntity<ProductResponse> findById(@PathVariable long id);

    @GetMapping("product/find/admin/id/{id}")
    ResponseEntity<ProductResponse> findByIdForAdmin(@PathVariable long id);

    @PostMapping(value = "product/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<ProductResponse> create(
            @RequestPart(value = "body") ProductRequest productRequest,
            @RequestPart(value = "file", required = false) List<MultipartFile> files
            ) throws IOException;

    @PutMapping("product/update/{id}")
    ResponseEntity<ProductResponse> update(@PathVariable long id, @RequestBody ProductRequest productRequest);

    @PutMapping(value = "product/image/add", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<ProductResponse> addToImageList(
            @RequestParam("id") Long id,
            @RequestPart("file") MultipartFile file
    );

    @DeleteMapping("product/image/delete/{productId}/{imageId}")
    ResponseEntity<MessageResponse> deleteFromImageList(@PathVariable long productId, @PathVariable long imageId);

    @DeleteMapping("product/delete/{id}")
    ResponseEntity<MessageResponse> delete(@PathVariable long id);
}
