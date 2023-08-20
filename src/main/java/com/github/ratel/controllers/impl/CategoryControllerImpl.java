package com.github.ratel.controllers.impl;

import com.github.ratel.controllers.ApiSecurityHeader;
import com.github.ratel.controllers.interfaces.CategoryController;
import com.github.ratel.entity.Category;
import com.github.ratel.payload.request.CategoryRequest;
import com.github.ratel.payload.response.CategoryResponse;
import com.github.ratel.payload.response.MessageResponse;
import com.github.ratel.services.CategoryService;
import com.github.ratel.utils.transfer_object.CategoryTransferObj;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@CrossOrigin("*")
@RequiredArgsConstructor
@RequestMapping("/app/shop/")
@RestController(value = "categoryControllerAdmin")
public class CategoryControllerImpl implements ApiSecurityHeader, CategoryController {

    private final CategoryService categoryService;

    @Override
    @CrossOrigin("*")
    public ResponseEntity<List<CategoryResponse>> readAllCategory() {
        return ResponseEntity.ok(this.categoryService.findAllCategory().stream()
                .sorted(Comparator.comparing(Category::getId))
                .map(CategoryTransferObj::fromCategory)
                .collect(Collectors.toList()));
    }

    @Override
    @CrossOrigin("*")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<List<CategoryResponse>> readAllCategoryForAdmin() {
        return ResponseEntity.ok(this.categoryService.findAllCategoryForAdmin().stream()
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
    public ResponseEntity<CategoryResponse> getById(Long id) {
        return ResponseEntity.ok(CategoryTransferObj.fromCategory(this.categoryService.getByIdForAdmin(id)));
    }

    @Override
    @CrossOrigin("*")
    public ResponseEntity<CategoryResponse> findByName(String name) {
        return ResponseEntity.ok(CategoryTransferObj.fromCategory(this.categoryService.findCategoryByName(name)));
    }

    @Override
    @CrossOrigin("*")
    @Secured({"ROLE_ADMIN", "ROLE_MANAGER"})
    public ResponseEntity<CategoryResponse> createCategory(CategoryRequest categoryRequest) {
        return ResponseEntity.ok(CategoryTransferObj.fromCategory(
                this.categoryService.createCategory(CategoryTransferObj.toCategory(categoryRequest)))
        );
    }

    @Override
    @CrossOrigin("*")
    @Secured({"ROLE_ADMIN", "ROLE_MANAGER"})
    public ResponseEntity<CategoryResponse> updateCategory(CategoryRequest categoryRequest, long id) {
        Category category = this.categoryService.findById(id);
        category.setName(categoryRequest.getName());
        return ResponseEntity.ok(CategoryTransferObj.fromCategory(this.categoryService.updateCategory(category)));
    }

    @Override
    @CrossOrigin("*")
    @Secured({"ROLE_ADMIN", "ROLE_MANAGER"})
    public ResponseEntity<MessageResponse> deleteCategory (long id) {
        this.categoryService.deleteCategoryById(id);
        return ResponseEntity.ok(new MessageResponse("Category deleted"));
    }
}

