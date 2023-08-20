package com.github.ratel.controllers.interfaces;

import com.github.ratel.payload.request.SubcategoryRequest;
import com.github.ratel.payload.response.MessageResponse;
import com.github.ratel.payload.response.SubcategoryResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@SecurityRequirement(name = "Authorization")
public interface SubcategoryController {

    @GetMapping("free/subcategory/all")
    ResponseEntity<List<SubcategoryResponse>> readAll(@RequestParam long categoryId);

    @GetMapping("subcategory/all/admin")
    ResponseEntity<List<SubcategoryResponse>> readAllForAdmin(@RequestParam long categoryId);

    @GetMapping("free/subcategory/id")
    ResponseEntity<SubcategoryResponse> findById(@RequestParam long id);

    @GetMapping("free/subcategory/name")
    ResponseEntity<SubcategoryResponse> findByName(@RequestParam String name);

    @GetMapping("subcategory/admin/id")
    ResponseEntity<SubcategoryResponse> getByIdForAdmin(@RequestParam long id);

    @GetMapping("subcategory/admin/name")
    ResponseEntity<SubcategoryResponse> getByNameForAdmin(@RequestParam String name);


    @PostMapping("subcategory/create")
    ResponseEntity<SubcategoryResponse> createSubcategory(@RequestBody SubcategoryRequest subcategoryRequest);

    @PutMapping("subcategory/update")
    ResponseEntity<SubcategoryResponse> updateSubcategory(@RequestBody SubcategoryRequest subcategoryRequest, @RequestParam long id);

    @DeleteMapping("subcategory/delete")
    ResponseEntity<MessageResponse> deleteSubcategory (@RequestParam long id);

}
