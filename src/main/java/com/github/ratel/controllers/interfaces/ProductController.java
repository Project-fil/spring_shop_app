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

    @GetMapping("free/product/find/code/admin/code/{code}")
    ResponseEntity<ProductResponse> findByVendorCode(@PathVariable String code);

    @GetMapping("product/find/code/admin/code/{code}")
    ResponseEntity<ProductResponse> findByVendorCodeForAdmin(@PathVariable String code);

    @PostMapping(value = "product/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<ProductResponse> create(
            @RequestPart(value = "body") ProductRequest productRequest,
            @RequestPart(value = "file", required = false) List<MultipartFile> files
            ) throws IOException;

    @PutMapping("product/update")
    ResponseEntity<ProductResponse> update(@RequestBody ProductRequest productRequest);

    @PutMapping("product/image")
    ResponseEntity<ProductResponse> updateImage(@RequestParam("id") long id, @RequestPart("file") MultipartFile file);

    @DeleteMapping("product/delete/{id}")
    ResponseEntity<MessageResponse> delete(@PathVariable long id);
}
