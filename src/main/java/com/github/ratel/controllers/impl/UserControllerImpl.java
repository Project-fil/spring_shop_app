package com.github.ratel.controllers.impl;

import com.github.ratel.controllers.ApiSecurityHeader;
import com.github.ratel.controllers.interfaces.UserController;
import com.github.ratel.entity.FileEntity;
import com.github.ratel.entity.User;
import com.github.ratel.handlers.FileHandler;
import com.github.ratel.payload.request.UserUpdateRequest;
import com.github.ratel.payload.response.MessageResponse;
import com.github.ratel.payload.response.UserResponse;
import com.github.ratel.services.CartService;
import com.github.ratel.services.FileService;
import com.github.ratel.services.UserService;
import com.github.ratel.utils.EntityUtil;
import com.github.ratel.utils.transfer_object.UserTransferObj;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.Date;
import java.util.Objects;

@RestController
@RequestMapping("/app/shop/")
@RequiredArgsConstructor
public class UserControllerImpl implements ApiSecurityHeader, UserController {

    private final UserService userService;

    private final CartService cartService;

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
    public ResponseEntity<Page<UserResponse>> findAllActiveUsers(
            int page,
            int size,
            String sortBy,
            String sortDirection
    ) {
        Sort.Direction direction = EntityUtil.getSortDirection(sortDirection);
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(direction, sortBy));
        return ResponseEntity.ok(this.userService.findAllUsers(pageRequest)
                .map(UserTransferObj::fromLazyUser));
    }

    @Override
    @CrossOrigin("*")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<Page<UserResponse>> findAllUsersForAdmin(
            int page,
            int size,
            String sortBy,
            String sortDirection
    ) {
        Sort.Direction direction = EntityUtil.getSortDirection(sortDirection);
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(direction, sortBy));
        return ResponseEntity.ok(this.userService.findAllUsersForAdmin(pageRequest)
                .map(UserTransferObj::fromUserForAdmin));
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
        return ResponseEntity.ok(UserTransferObj.fromUserForAdmin(this.userService.findUserForAdmin(userId)));
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
        user.setFile(fileEntity);
        return ResponseEntity.ok(UserTransferObj.fromUser(this.userService.updateUser(user)));
    }

    @Override
    @Transactional
    @CrossOrigin("*")
    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    public ResponseEntity<MessageResponse> deleteUser(Long userId) {
        this.userService.deleteUserById(userId);
        this.cartService.deleteCartByUserId(userId);
        return ResponseEntity.ok(new MessageResponse(
                "User with id " + userId + " deleted",
                new Date()
        ));
    }
}
