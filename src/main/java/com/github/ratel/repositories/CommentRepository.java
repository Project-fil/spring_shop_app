package com.github.ratel.repositories;

import com.github.ratel.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    Page<Comment> findAllByProductIdAndRemovedFalse(Long productId, Pageable pageable);

    Page<Comment> findAllByAuthorIdAndRemovedFalse(Long userId, Pageable pageable);

}