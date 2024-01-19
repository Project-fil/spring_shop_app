package com.github.ratel.controllers.impl;

import com.github.ratel.controllers.ApiSecurityHeader;
import com.github.ratel.controllers.interfaces.SubcategoryController;
import com.github.ratel.entity.Category;
import com.github.ratel.entity.Subcategory;
import com.github.ratel.payload.response.MessageResponse;
import com.github.ratel.payload.response.SubcategoryResponse;
import com.github.ratel.services.CategoryService;
import com.github.ratel.services.SubcategoryService;
import com.github.ratel.utils.EntityUtil;
import com.github.ratel.utils.transfer_object.SubcategoryTransferObj;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
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
    public ResponseEntity<List<SubcategoryResponse>> findAll(long categoryId, String sortBy, String sortDirection) {
        Category category = this.categoryService.findById(categoryId);
        Sort.Direction direction = EntityUtil.getSortDirection(sortDirection);
        Sort sort = Sort.by(direction, sortBy);
        return ResponseEntity.ok(this.subcategoryService.findAllByCategory(category, sort).stream()
                .map(SubcategoryTransferObj::fromLazySubcategory)
                .collect(Collectors.toList()));
    }

    @Override
    @CrossOrigin("*")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<List<SubcategoryResponse>> findAllForAdmin(long categoryId, int limit) {
        return ResponseEntity.ok(this.subcategoryService.findAllForAdmin(categoryId, limit).stream()
                .map(SubcategoryTransferObj::fromSubcategory)
                .collect(Collectors.toList()));
    }

    @Override
    @CrossOrigin("*")
    public ResponseEntity<SubcategoryResponse> findById(long id) {
        return ResponseEntity.ok(SubcategoryTransferObj.fromSubcategory(this.subcategoryService.findById(id)));
    }

    @Override
    @CrossOrigin("*")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<SubcategoryResponse> findByIdForAdmin(long id) {
        return ResponseEntity.ok(
                SubcategoryTransferObj.fromSubcategoryForAdmin(this.subcategoryService.getByIdForAdmin(id))
        );
    }

    @Override
    @Transactional
    @CrossOrigin("*")
    @Secured({"ROLE_ADMIN", "ROLE_MANAGER"})
    public ResponseEntity<SubcategoryResponse> createSubcategory(long categoryId, String name) {
        Category category = this.categoryService.findById(categoryId);
        Subcategory subcategory = new Subcategory();
        subcategory.setName(name);
        subcategory.setCategory(category);
        return ResponseEntity.ok(SubcategoryTransferObj.fromSubcategory(this.subcategoryService.create(subcategory)));
    }

    @Override
    @CrossOrigin("*")
    @Secured({"ROLE_ADMIN", "ROLE_MANAGER"})
    public ResponseEntity<SubcategoryResponse> updateSubcategory(long subCategoryId, String name) {
        Subcategory subcategory = this.subcategoryService.findById(subCategoryId);
        subcategory.setName(name);
        return ResponseEntity.ok(SubcategoryTransferObj.fromSubcategory(this.subcategoryService.update(subcategory)));
    }

    @Override
    @CrossOrigin("*")
    @Secured({"ROLE_ADMIN", "ROLE_MANAGER"})
    public ResponseEntity<MessageResponse> deleteSubcategory(long id) {
        this.subcategoryService.delete(id);
        return ResponseEntity.ok(new MessageResponse(
                "Subcategory deleted",
                new Date()
        ));
    }
}
