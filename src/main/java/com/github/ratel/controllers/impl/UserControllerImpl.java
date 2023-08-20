package com.github.ratel.controllers.impl;

import com.github.ratel.controllers.ApiSecurityHeader;
import com.github.ratel.controllers.interfaces.UserController;
import com.github.ratel.entity.FileEntity;
import com.github.ratel.entity.User;
import com.github.ratel.handlers.FileHandler;
import com.github.ratel.payload.request.UserUpdateRequest;
import com.github.ratel.payload.response.UserResponse;
import com.github.ratel.services.UserService;
import com.github.ratel.utils.transfer_object.UserTransferObj;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.security.Principal;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/app/shop/")
@RequiredArgsConstructor
public class UserControllerImpl implements ApiSecurityHeader, UserController {

    private final UserService userService;

    private final FileHandler fileHandler;

    @Override
    @CrossOrigin("*")
    public ResponseEntity<UserResponse> getCurrentUser(Principal principal) {
        User user = this.userService.getCurrentUser(principal);
        return ResponseEntity.ok(UserTransferObj.fromUser(user));
    }

    @Override
    @CrossOrigin("*")
    @Secured({"ROLE_ADMIN", "ROLE_MANAGER"})
    public ResponseEntity<List<UserResponse>> findAllActiveUsers() {
        return ResponseEntity.ok(this.userService.findAllUsers().stream()
                .map(UserTransferObj::fromUser)
                .collect(Collectors.toList())
        );
    }

    @Override
    @CrossOrigin("*")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<List<UserResponse>> findAllUsersForAdmin() {
        return ResponseEntity.ok(this.userService.findAllUsersForAdmin().stream()
                .map(UserTransferObj::fromUser)
                .collect(Collectors.toList())
        );
    }

    @Override
    @CrossOrigin("*")
    @Secured({"ROLE_ADMIN", "ROLE_MANAGER"})
    public ResponseEntity<UserResponse> findUserById(Long userId) {
        return ResponseEntity.ok(UserTransferObj.fromUser(this.userService.findById(userId)));
    }

    @Override
    public ResponseEntity<UserResponse> getUserByIdForAdmin(Long userId) {
        return ResponseEntity.ok(UserTransferObj.fromUser(this.userService.findUserForAdmin(userId)));
    }

    @Override
    @CrossOrigin("*")
    @Transactional
    public ResponseEntity<UserResponse> update(UserUpdateRequest updateRequest, MultipartFile image) {
        User user = this.userService.findById(updateRequest.getId());
        UserTransferObj.updateUser(user, updateRequest);// test for save address
        FileEntity fileEntity = null;
        if (Objects.nonNull(image)) {
            fileEntity = this.fileHandler.writeFile(image);
        }
        user.setFileEntity(fileEntity);
        return ResponseEntity.ok(UserTransferObj.fromUser(this.userService.updateUser(user)));
    }

    @Override
    @CrossOrigin("*")
    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    public void deleteUser(Long userId) {
        this.userService.deleteUserById(userId);
    }
}
