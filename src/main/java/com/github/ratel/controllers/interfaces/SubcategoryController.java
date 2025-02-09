package com.github.ratel.controllers.interfaces;

import com.github.ratel.payload.response.MessageResponse;
import com.github.ratel.payload.response.SubcategoryResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.github.ratel.utils.ApiPathConstants.SUBCATEGORY;

@SecurityRequirement(name = "Authorization")
public interface SubcategoryController {

    @GetMapping("/free" + SUBCATEGORY)
    ResponseEntity<List<SubcategoryResponse>> findAll(
            @RequestParam long categoryId,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDirection
    );

    @GetMapping(SUBCATEGORY + "/admin/category/{categoryId}/all/{limit}")
    ResponseEntity<List<SubcategoryResponse>> findAllForAdmin(@PathVariable long categoryId, @PathVariable int limit);

    @GetMapping("/free" + SUBCATEGORY + "{id}")
    ResponseEntity<SubcategoryResponse> findById(@PathVariable long id);

    @GetMapping(SUBCATEGORY + "/admin/subcategory/{id}")
    ResponseEntity<SubcategoryResponse> findByIdForAdmin(@PathVariable long id);

    @PostMapping(SUBCATEGORY + "/category/{categoryId}")
    ResponseEntity<SubcategoryResponse> createSubcategory(@PathVariable long categoryId, @RequestBody String name);

    @PutMapping(SUBCATEGORY + "/{subCategoryId}}")
    ResponseEntity<SubcategoryResponse> updateSubcategory(@PathVariable long subCategoryId, @RequestBody String name);

    @DeleteMapping(SUBCATEGORY)
    ResponseEntity<MessageResponse> deleteSubcategory(@RequestParam long id);

}
