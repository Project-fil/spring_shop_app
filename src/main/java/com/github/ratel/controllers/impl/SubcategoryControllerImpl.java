package com.github.ratel.controllers.impl;

import com.github.ratel.controllers.ApiSecurityHeader;
import com.github.ratel.controllers.interfaces.SubcategoryController;
import com.github.ratel.entity.Category;
import com.github.ratel.entity.Subcategory;
import com.github.ratel.payload.request.SubcategoryRequest;
import com.github.ratel.payload.response.MessageResponse;
import com.github.ratel.payload.response.SubcategoryResponse;
import com.github.ratel.services.CategoryService;
import com.github.ratel.services.SubcategoryService;
import com.github.ratel.utils.transfer_object.SubcategoryTransferObj;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@CrossOrigin("*")
@RequiredArgsConstructor
@RequestMapping("/app/shop/")
@RestController(value = "subcategoryControllerAdmin")
public class SubcategoryControllerImpl implements ApiSecurityHeader, SubcategoryController {

    private final CategoryService categoryService;

    private final SubcategoryService subcategoryService;

    @Override
    @CrossOrigin("*")
    public ResponseEntity<List<SubcategoryResponse>> readAll(long categoryId) {
        Category category = this.categoryService.findById(categoryId);
        return ResponseEntity.ok(this.subcategoryService.findByAll(category).stream()
                .sorted(Comparator.comparing(Subcategory::getId))
                .map(SubcategoryTransferObj::fromSubcategory)
                .collect(Collectors.toList())
        );
    }

    @Override
    @CrossOrigin("*")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<List<SubcategoryResponse>> readAllForAdmin(long categoryId) {
        Category category = this.categoryService.findById(categoryId);
        return ResponseEntity.ok(this.subcategoryService.findByAllForAdmin(category).stream()
                .map(SubcategoryTransferObj::fromSubcategory)
                .collect(Collectors.toList())
        );
    }

    @Override
    @CrossOrigin("*")
    public ResponseEntity<SubcategoryResponse> findById(long id) {
        return ResponseEntity.ok(SubcategoryTransferObj.fromSubcategory(this.subcategoryService.findById(id)));
    }

    @Override
    @CrossOrigin("*")
    public ResponseEntity<SubcategoryResponse> findByName(String name) {
        return ResponseEntity.ok(SubcategoryTransferObj.fromSubcategory(this.subcategoryService.findByName(name)));
    }

    @Override
    @CrossOrigin("*")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<SubcategoryResponse> getByIdForAdmin(long id) {
        Subcategory getSubcategory = null;
        try {
            getSubcategory = this.subcategoryService.findById(id);
        } catch (Exception ignore) {}
        if (Objects.isNull(getSubcategory)) {
            getSubcategory = this.subcategoryService.getById(id);
        }
        return ResponseEntity.ok(SubcategoryTransferObj.fromSubcategory(getSubcategory));
    }

    @Override
    @CrossOrigin("*")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<SubcategoryResponse> getByNameForAdmin(String name) {
        Subcategory getByName = null;
        try {
            getByName = this.subcategoryService.findByName(name);
        }catch (Exception ignore) {}
        if (Objects.isNull(getByName)) {
            getByName = this.subcategoryService.getByName(name);
        }
        return ResponseEntity.ok(SubcategoryTransferObj.fromSubcategory(getByName));
    }

    @Override
    @Transactional
    @CrossOrigin("*")
    @Secured({"ROLE_ADMIN", "ROLE_MANAGER"})
    public ResponseEntity<SubcategoryResponse> createSubcategory(SubcategoryRequest subcategoryRequest) {
        Subcategory subcategory = SubcategoryTransferObj.toSubcategory(subcategoryRequest);
        Category category = this.categoryService.findById(subcategoryRequest.getCategoryId());
        subcategory.setCategory(category);
        return ResponseEntity.ok(SubcategoryTransferObj.fromSubcategory(this.subcategoryService.create(subcategory)));
    }

    @Override
    @CrossOrigin("*")
    @Secured({"ROLE_ADMIN", "ROLE_MANAGER"})
    public ResponseEntity<SubcategoryResponse> updateSubcategory(SubcategoryRequest subcategoryRequest, long id) {
        Subcategory subcategory = this.subcategoryService.findById(id);
        subcategory.setName(subcategoryRequest.getName());
        return ResponseEntity.ok(SubcategoryTransferObj.fromSubcategory(this.subcategoryService.update(subcategory)));
    }

    @Override
    @CrossOrigin("*")
    @Secured({"ROLE_ADMIN", "ROLE_MANAGER"})
    public ResponseEntity<MessageResponse> deleteSubcategory(long id) {
        this.subcategoryService.delete(id);
        return ResponseEntity.ok(new MessageResponse("Subcategory deleted"));
    }
}
