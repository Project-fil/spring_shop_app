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

import static com.github.ratel.utils.ApiPathConstants.PRODUCT;

@SecurityRequirement(name = "Authorization")
public interface ProductController {

    @GetMapping("/free" + PRODUCT + "/subcategory/{subcategoryId}")
    ResponseEntity<Page<ProductResponse>> findAll(
            @RequestParam long subcategoryId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDirection
    );

    @GetMapping(PRODUCT + "/admin/all")
    ResponseEntity<Page<ProductResponse>> findAllForAdmin(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDirection
    );

    @GetMapping("/free" + PRODUCT + "/{id}")
    ResponseEntity<ProductResponse> findById(@PathVariable long id);

    @GetMapping(PRODUCT + "/admin/productId/{id}")
    ResponseEntity<ProductResponse> findByIdForAdmin(@PathVariable long id);

    @PostMapping(value = PRODUCT, consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<ProductResponse> create(
            @RequestPart(value = "body") ProductRequest productRequest,
            @RequestPart(value = "file", required = false) List<MultipartFile> files
    ) throws IOException;

    @PutMapping(PRODUCT + "/{id}")
    ResponseEntity<ProductResponse> update(@PathVariable long id, @RequestBody ProductRequest productRequest);

    @PutMapping(value = PRODUCT + "/image/add", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<ProductResponse> addToImageList(
            @RequestParam("productId") long productId,
            @RequestPart(value = "file", required = false) List<MultipartFile> files
    );

    @DeleteMapping(PRODUCT + "/{productId}/images")
    ResponseEntity<ProductResponse> deleteFromImageList(
            @PathVariable long productId,
            @RequestBody List<Long> imageIdsList
    );

    @DeleteMapping(PRODUCT + "/{id}")
    ResponseEntity<MessageResponse> delete(@PathVariable long id);
}
