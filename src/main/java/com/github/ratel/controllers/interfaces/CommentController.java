package com.github.ratel.controllers.interfaces;

import com.github.ratel.payload.request.CommentRequest;
import com.github.ratel.payload.request.CommentUpdateRequest;
import com.github.ratel.payload.response.CommentResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@SecurityRequirement(name = "Authorization")
public interface CommentController {

    @GetMapping("/product")
    ResponseEntity<Page<CommentResponse>> findAllByProductId(
            @RequestParam Long productId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDirection
    );

    @GetMapping("/user")
    ResponseEntity<Page<CommentResponse>> findAllByUserId(
            @RequestParam Long UserId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDirection
    );

    @GetMapping("/{id}")
    ResponseEntity<CommentResponse> findById(@PathVariable Long id);

    @PostMapping()
    ResponseEntity<CommentResponse> create(@RequestBody CommentRequest request);

    @PutMapping()
    ResponseEntity<CommentResponse> update(@RequestBody CommentUpdateRequest updateRequest);

    @DeleteMapping("/{commentId}")
    ResponseEntity<Object> delete(@PathVariable Long commentId);
}
