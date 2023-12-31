package com.github.ratel.controllers.impl;

import com.github.ratel.controllers.ApiSecurityHeader;
import com.github.ratel.controllers.interfaces.UserController;
import com.github.ratel.entity.FileEntity;
import com.github.ratel.entity.User;
import com.github.ratel.handlers.FileHandler;
import com.github.ratel.payload.request.UserUpdateRequest;
import com.github.ratel.payload.response.MessageResponse;
import com.github.ratel.payload.response.UserResponse;
import com.github.ratel.services.FileService;
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

    private final FileService fileService;

    private final FileHandler fileHandler;

    @Override
    @CrossOrigin("*")
    @Secured({"ROLE_ADMIN", "ROLE_MANAGER", "ROLE_USER"})
    public ResponseEntity<UserResponse> getCurrentUser(Principal principal) {
        User user = this.userService.getCurrentUser(principal);
        return ResponseEntity.ok(UserTransferObj.fromUser(user));
    }

    @Override
    @CrossOrigin("*")
    @Secured({"ROLE_ADMIN", "ROLE_MANAGER"})
    public ResponseEntity<List<UserResponse>> findAllActiveUsers() {
        return ResponseEntity.ok(this.userService.findAllUsers().stream()
                .map(UserTransferObj::fromLazyUser)
                .collect(Collectors.toList())
        );
    }

    @Override
    @CrossOrigin("*")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<List<UserResponse>> findAllUsersForAdmin() {
        return ResponseEntity.ok(this.userService.findAllUsersForAdmin().stream()
                .map(UserTransferObj::fromUserForAdmin)
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
    @CrossOrigin("*")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<UserResponse> getUserByIdForAdmin(Long userId) {
        User getUser = null;
        try {
            getUser = this.userService.findById(userId);
        } catch (Exception ignore) {}
        if (Objects.isNull(getUser)) {
            getUser = this.userService.findUserForAdmin(userId);
        }
        return ResponseEntity.ok(UserTransferObj.fromUserForAdmin(getUser));
    }

    @Override
    @Transactional
    @CrossOrigin("*")
    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    public ResponseEntity<UserResponse> update(UserUpdateRequest updateRequest, MultipartFile image) {
        User user = this.userService.findById(updateRequest.getId());
        UserTransferObj.updateUser(user, updateRequest);
        FileEntity fileEntity = null;
        if (Objects.nonNull(image)) {
            fileEntity = this.fileService.create(this.fileHandler.writeFile(image));
            if (Objects.nonNull(user.getFileEntity())) this.fileService.deleteById(user.getFileEntity().getId());
        }
        user.setFileEntity(fileEntity);
        return ResponseEntity.ok(UserTransferObj.fromUser(this.userService.updateUser(user)));
    }

    @Override
    @Transactional
    @CrossOrigin("*")
    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    public ResponseEntity<MessageResponse> deleteUser(Long userId) {
        return ResponseEntity.ok(new MessageResponse(this.userService.deleteUserById(userId)));
    }
}
