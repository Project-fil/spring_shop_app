package com.github.ratel.controllers.interfaces;

import com.github.ratel.payload.response.CategoryResponse;
import com.github.ratel.payload.response.MessageResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.github.ratel.utils.ApiPathConstants.CATEGORY;

@SecurityRequirement(name = "Authorization")
public interface CategoryController {

    @GetMapping("free/categories")
    ResponseEntity<List<CategoryResponse>> findAllCategory(
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDirection
    );

    @GetMapping(CATEGORY + "/admin/{limit}")
    ResponseEntity<List<CategoryResponse>> findAllCategoryForAdmin(@PathVariable int limit);

    @GetMapping("free/categories/{id}")
    ResponseEntity<CategoryResponse> findById(@PathVariable Long id);

    @GetMapping(CATEGORY + "/admin/categoriesId/{id}")
    ResponseEntity<CategoryResponse> findByIdForAdmin(@PathVariable Long id);

    @PostMapping(CATEGORY)
    ResponseEntity<CategoryResponse> createCategory(@RequestBody String name);

    @PutMapping(CATEGORY + "/{id}")
    ResponseEntity<CategoryResponse> updateCategory(@PathVariable long id, @RequestBody String name);

    @DeleteMapping(CATEGORY + "/{id}")
    ResponseEntity<MessageResponse> deleteCategory(@PathVariable long id);

}
