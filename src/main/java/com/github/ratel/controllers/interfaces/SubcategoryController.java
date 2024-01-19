package com.github.ratel.controllers.interfaces;

import com.github.ratel.payload.response.MessageResponse;
import com.github.ratel.payload.response.SubcategoryResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@SecurityRequirement(name = "Authorization")
public interface SubcategoryController {

    @GetMapping("free/subcategory/all")
    ResponseEntity<List<SubcategoryResponse>> findAll(
            @RequestParam long categoryId,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDirection
    );

    @GetMapping("subcategory/all/admin/{categoryId}/{limit}")
    ResponseEntity<List<SubcategoryResponse>> findAllForAdmin(@PathVariable long categoryId, @PathVariable int limit);

    @GetMapping("free/subcategory/id/{id}")
    ResponseEntity<SubcategoryResponse> findById(@PathVariable long id);

    @GetMapping("subcategory/admin/id/{id}")
    ResponseEntity<SubcategoryResponse> findByIdForAdmin(@PathVariable long id);

    @PostMapping("subcategory/create/{categoryId}/{name}")
    ResponseEntity<SubcategoryResponse> createSubcategory(@PathVariable long categoryId, @PathVariable String name);

    @PutMapping("subcategory/update/{subCategoryId}}/{name}")
    ResponseEntity<SubcategoryResponse> updateSubcategory(@PathVariable long subCategoryId, @PathVariable String name);

    @DeleteMapping("subcategory/delete")
    ResponseEntity<MessageResponse> deleteSubcategory(@RequestParam long id);

}
