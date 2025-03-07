package com.github.ratel.controllers.impl;

import com.github.ratel.controllers.ApiSecurityHeader;
import com.github.ratel.controllers.interfaces.CommentController;
import com.github.ratel.payload.request.CommentRequest;
import com.github.ratel.payload.request.CommentUpdateRequest;
import com.github.ratel.payload.response.CommentResponse;
import com.github.ratel.payload.response.MessageResponse;
import com.github.ratel.services.CommentService;
import com.github.ratel.utils.EntityUtil;
import com.github.ratel.utils.transfer_object.CommentTransferObj;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

import static com.github.ratel.utils.ApiPathConstants.API_PREFIX;
import static com.github.ratel.utils.ApiPathConstants.COMMENT;

@CrossOrigin("*")
@RestController
@RequestMapping(API_PREFIX + COMMENT)
@RequiredArgsConstructor
public class CommentControllerImpl implements ApiSecurityHeader, CommentController {

    private final CommentService commentService;

    @Override
    @Secured({"ROLE_ADMIN", "ROLE_MANAGER", "ROLE_USER"})
    public ResponseEntity<Page<CommentResponse>> findAllByProductId(
            Long productId,
            int page,
            int size,
            String sortBy,
            String sortDirection
    ) {
        PageRequest pageRequest = EntityUtil.getPageRequest(page, size, sortBy, sortDirection);
        return ResponseEntity.ok(this.commentService.findAllByProductId(productId, pageRequest)
                .map(CommentTransferObj::fromComment));
    }

    @Override
    @Secured({"ROLE_ADMIN", "ROLE_MANAGER"})
    public ResponseEntity<Page<CommentResponse>> findAllByUserId(
            Long userId,
            int page,
            int size,
            String sortBy,
            String sortDirection
    ) {
        PageRequest pageRequest = EntityUtil.getPageRequest(page, size, sortBy, sortDirection);
        return ResponseEntity.ok(this.commentService.findAllByUserId(userId, pageRequest)
                .map(CommentTransferObj::fromComment));
    }

    @Override
    @Secured({"ROLE_ADMIN", "ROLE_MANAGER"})
    public ResponseEntity<CommentResponse> findById(Long id) {
        return ResponseEntity.ok(CommentTransferObj.fromComment(this.commentService.findBiId(id)));
    }

    @Override
    @Secured({"ROLE_ADMIN", "ROLE_MANAGER", "ROLE_USER"})
    public ResponseEntity<CommentResponse> create(CommentRequest request) {
        return ResponseEntity.ok(CommentTransferObj.fromComment(this.commentService.create(request)));
    }

    @Override
    @Secured({"ROLE_ADMIN", "ROLE_MANAGER"})
    public ResponseEntity<CommentResponse> update(CommentUpdateRequest updateRequest) {
        return ResponseEntity.ok(CommentTransferObj.fromComment(this.commentService.update(updateRequest)));
    }

    @Override
    @Secured({"ROLE_ADMIN", "ROLE_MANAGER"})
    public ResponseEntity<Object> delete(Long commentId) {
        this.commentService.delete(commentId);
        return ResponseEntity.ok(
                new MessageResponse(String.format("Comment with id(%s) was deleted", commentId), new Date())
        );
    }
}
