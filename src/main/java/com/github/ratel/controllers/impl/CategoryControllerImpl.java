package com.github.ratel.controllers.impl;

import com.github.ratel.controllers.ApiSecurityHeader;
import com.github.ratel.controllers.interfaces.CategoryController;
import com.github.ratel.entity.Category;
import com.github.ratel.payload.response.CategoryResponse;
import com.github.ratel.payload.response.MessageResponse;
import com.github.ratel.services.CategoryService;
import com.github.ratel.utils.EntityUtil;
import com.github.ratel.utils.transfer_object.CategoryTransferObj;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RequestMapping("/app/shop/")
@RestController(value = "categoryControllerAdmin")
public class CategoryControllerImpl implements ApiSecurityHeader, CategoryController {

    private final CategoryService categoryService;

    @Override
    @CrossOrigin("*")
    public ResponseEntity<List<CategoryResponse>> findAllCategory(String sortBy, String sortDirection) {
        Sort.Direction direction = EntityUtil.getSortDirection(sortDirection);
        return ResponseEntity.ok(this.categoryService.findAllCategory(Sort.by(direction, sortBy)).stream()
                .map(CategoryTransferObj::fromLazyCategory)
                .collect(Collectors.toList()));
    }

    @Override
    @CrossOrigin("*")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<List<CategoryResponse>> findAllCategoryForAdmin(int limit) {
        return ResponseEntity.ok(this.categoryService.findAllCategoryForAdmin(limit).stream()
                .map(CategoryTransferObj::fromCategory)
                .collect(Collectors.toList())
        );
    }

    @Override
    @CrossOrigin("*")
    public ResponseEntity<CategoryResponse> findById(Long id) {
        return ResponseEntity.ok(CategoryTransferObj.fromCategory(this.categoryService.findById(id)));
    }

    @Override
    @CrossOrigin("*")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<CategoryResponse> findByIdForAdmin(Long id) {
        return ResponseEntity.ok(CategoryTransferObj.fromCategoryForAdmin(this.categoryService.getByIdForAdmin(id)));
    }

    @Override
    @CrossOrigin("*")
    @Secured({"ROLE_ADMIN", "ROLE_MANAGER"})
    public ResponseEntity<CategoryResponse> createCategory(String name) {
        return ResponseEntity.ok(CategoryTransferObj.fromCategory(this.categoryService.createCategory(name)));
    }

    @Override
    @CrossOrigin("*")
    @Secured({"ROLE_ADMIN", "ROLE_MANAGER"})
    public ResponseEntity<CategoryResponse> updateCategory(long id, String name) {
        return ResponseEntity.ok(CategoryTransferObj.fromCategory(this.categoryService.updateCategory(id, name)));
    }

    @Override
    @CrossOrigin("*")
    @Secured({"ROLE_ADMIN", "ROLE_MANAGER"})
    public ResponseEntity<MessageResponse> deleteCategory(long id) {
        this.categoryService.deleteCategoryById(id);
        return ResponseEntity.ok(new MessageResponse(
                "Category deleted",
                new Date()
        ));
    }
}

