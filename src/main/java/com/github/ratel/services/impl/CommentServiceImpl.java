package com.github.ratel.services.impl;

import com.github.ratel.entity.Comment;
import com.github.ratel.entity.Product;
import com.github.ratel.entity.User;
import com.github.ratel.exceptions.AppException;
import com.github.ratel.exceptions.EntityNotFoundException;
import com.github.ratel.payload.request.CommentRequest;
import com.github.ratel.payload.request.CommentUpdateRequest;
import com.github.ratel.repositories.CommentRepository;
import com.github.ratel.services.CommentService;
import com.github.ratel.services.ProductService;
import com.github.ratel.services.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final ProductService productService;
    private final UserService userService;

    @Override
    @Transactional(readOnly = true)
    public Page<Comment> findAllByProductId(Long productId, Pageable pageable) {
        if (ObjectUtils.anyNull(productId, pageable)) {
            throw new AppException("Invalid params value: productId(%s), pageable(%s)", productId, pageable);
        }
        return this.commentRepository.findAllByProductIdAndRemovedFalse(productId, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Comment> findAllByUserId(Long userId, Pageable pageable) {
        if (ObjectUtils.anyNull(userId, pageable)) {
            throw new AppException("Invalid params value: userId(%s), pageable(%s)", userId, pageable);
        }
        return this.commentRepository.findAllByAuthorIdAndRemovedFalse(userId, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Comment findBiId(Long id) {
        if (Objects.isNull(id)) {
            throw new AppException("Invalid params value: commentId(%s)", id);
        }
        return this.commentRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Comment with id(%s) not found", id)
        );
    }

    @Override
    @Transactional
    public Comment create(CommentRequest request) {
        if (Objects.isNull(request)) {
            throw new AppException("Invalid params value: request(%s)", request);
        }
        Comment newComment = new Comment();
        Product product = this.productService.findById(request.getProductId());
        User user = this.userService.findById(request.getUserId());
        newComment.setAuthor(user);
        newComment.setProduct(product);
        newComment.setText(request.getText());
        Comment savedComment = this.commentRepository.save(newComment);
        product.addComment(savedComment);
        user.addComment(savedComment);
        this.productService.update(product);
        this.userService.updateUser(user);
        return savedComment;
    }

    @Override
    @Transactional
    public Comment update(CommentUpdateRequest request) {
        if (Objects.isNull(request)) {
            throw new AppException("Invalid params value: request(%s)", request);
        }
        Comment comment = this.commentRepository.findById(request.getCommentId()).orElseThrow(
                () -> new EntityNotFoundException("Comment with id(%s) not found", request.getCommentId())
        );
        comment.setText(request.getText());
        return this.commentRepository.save(comment);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (Objects.isNull(id)) {
            throw new AppException("Invalid params value: id(%s)", id);
        }
        this.commentRepository.deleteById(id);
    }

}
