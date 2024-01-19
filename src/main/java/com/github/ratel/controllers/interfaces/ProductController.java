package com.github.ratel.controllers.interfaces;

import com.github.ratel.payload.request.ProductRequest;
import com.github.ratel.payload.response.MessageResponse;
import com.github.ratel.payload.response.ProductResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@SecurityRequirement(name = "Authorization")
public interface ProductController {

    @GetMapping("free/product/find/all/{subcategoryId}")
    ResponseEntity<Page<ProductResponse>> findAll(
            @RequestParam long subcategoryId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDirection
    );

    @GetMapping("product/find/all/admin")
    ResponseEntity<Page<ProductResponse>> findAllForAdmin(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDirection
    );

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
            @RequestPart(value = "file", required = false) List<MultipartFile> files
    );

    @DeleteMapping("product/image/delete/{productId}")
    ResponseEntity<ProductResponse> deleteFromImageList(@PathVariable long productId, @RequestBody List<Long> imageIdsList);

    @DeleteMapping("product/delete/{id}")
    ResponseEntity<MessageResponse> delete(@PathVariable long id);
}
