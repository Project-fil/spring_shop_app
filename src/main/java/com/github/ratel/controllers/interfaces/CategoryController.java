package com.github.ratel.controllers.interfaces;

import com.github.ratel.payload.response.CategoryResponse;
import com.github.ratel.payload.response.MessageResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@SecurityRequirement(name = "Authorization")
public interface CategoryController {

    @GetMapping("free/category/all")
    ResponseEntity<List<CategoryResponse>> readAllCategory();

    @GetMapping("category/admin")
    ResponseEntity<List<CategoryResponse>> readAllCategoryForAdmin();

    @GetMapping("free/category/id/{id}")
    ResponseEntity<CategoryResponse> findById(@PathVariable Long id);

    @GetMapping("category/admin/id/{id}")
    ResponseEntity<CategoryResponse> getByIdForAdmin(@PathVariable Long id);

    @PostMapping("category/create/{name}")
    ResponseEntity<CategoryResponse> createCategory(@PathVariable String name);

    @PutMapping("category/update/{id}/{name}")
    ResponseEntity<CategoryResponse> updateCategory(@PathVariable long id, @PathVariable String name);

    @DeleteMapping("category/delete")
    ResponseEntity<MessageResponse> deleteCategory (@RequestParam long id);

}
