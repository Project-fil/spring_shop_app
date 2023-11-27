package com.github.ratel.controllers.interfaces;

import com.github.ratel.payload.request.UserUpdateRequest;
import com.github.ratel.payload.response.MessageResponse;
import com.github.ratel.payload.response.UserResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@SecurityRequirement(name = "Authorization")
public interface UserController {

    @GetMapping("user")
    ResponseEntity<UserResponse> getCurrentUser(Principal principal);

    @GetMapping("/user/all")
    ResponseEntity<List<UserResponse>> findAllActiveUsers();

    @GetMapping("/user/all/admin")
    ResponseEntity<List<UserResponse>> findAllUsersForAdmin();

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
