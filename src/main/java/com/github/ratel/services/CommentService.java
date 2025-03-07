package com.github.ratel.services;

import com.github.ratel.entity.Comment;
import com.github.ratel.payload.request.CommentRequest;
import com.github.ratel.payload.request.CommentUpdateRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CommentService {

    Page<Comment> findAllByProductId(Long productId, Pageable pageable);

    Page<Comment> findAllByUserId(Long userId, Pageable pageable);

    Comment findBiId(Long id);

    Comment create(CommentRequest request);

    Comment update(CommentUpdateRequest request);

    void delete(Long id);

}
