package com.github.ratel.controllers.interfaces;

import com.github.ratel.payload.request.UserUpdateRequest;
import com.github.ratel.payload.response.MessageResponse;
import com.github.ratel.payload.response.UserResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.security.Principal;

@SecurityRequirement(name = "Authorization")
public interface UserController {

    @GetMapping("user")
    ResponseEntity<UserResponse> getCurrentUser(Principal principal);

    @GetMapping("/user/all")
    ResponseEntity<Page<UserResponse>> findAllActiveUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDirection
    );

    @GetMapping("/user/all/admin")
    ResponseEntity<Page<UserResponse>> findAllUsersForAdmin(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDirection
    );

    @GetMapping("/{userId}")
    ResponseEntity<UserResponse> findUserById(@PathVariable Long userId);

    @GetMapping("admin/{userId}")
    ResponseEntity<UserResponse> getUserByIdForAdmin(@PathVariable Long userId);

    @PutMapping(value = "user/update", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<UserResponse> update(
            @Valid @RequestPart("body") UserUpdateRequest updateRequest,
            @RequestPart(value = "image", required = false) MultipartFile image
            );

    @DeleteMapping("/{userId}")
    ResponseEntity<MessageResponse> deleteUser(@PathVariable Long userId);

}
