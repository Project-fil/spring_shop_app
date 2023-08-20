package com.github.ratel.controllers.interfaces;

import com.github.ratel.payload.request.CategoryRequest;
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

    @GetMapping("free/category/id")
    ResponseEntity<CategoryResponse> findById(@RequestParam Long id);

    @GetMapping("category/admin/id")
    ResponseEntity<CategoryResponse> getById(@RequestParam Long id);

    @GetMapping("free/category/name")
    ResponseEntity<CategoryResponse> findByName(@RequestParam String name);

    @PostMapping("category/create")
    ResponseEntity<CategoryResponse> createCategory(@RequestBody CategoryRequest categoryRequest);

    @PutMapping("category/update")
    ResponseEntity<CategoryResponse> updateCategory(@RequestBody CategoryRequest categoryRequest, @RequestParam long id);

    @DeleteMapping("category/delete")
    ResponseEntity<MessageResponse> deleteCategory (@RequestParam long id);

}
