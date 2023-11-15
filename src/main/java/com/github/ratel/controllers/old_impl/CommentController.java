//package com.github.ratel.controllers.impl;
//
//import com.github.ratel.controllers.ApiSecurityHeader;
//import com.github.ratel.payload.dto.CommentDto;
//import com.github.ratel.entity.Comment;
//import com.github.ratel.services.impl.CommentServiceImpl;
//import io.swagger.v3.oas.annotations.security.SecurityRequirement;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//import java.util.Optional;
//
//@CrossOrigin
//@RestController
//@RequestMapping(path = "/comments")
//public class CommentController implements ApiSecurityHeader {
//
//    @Autowired
//    private CommentServiceImpl commentServiceImpl;
//
//    @GetMapping
//    @SecurityRequirement(name = "Authorization")
//    public List<Comment> findAllComments() {
//        return this.commentServiceImpl.findAllComments();
//    }
//
//    @GetMapping("/{commentsId}")
//    @SecurityRequirement(name = "Authorization")
//    public Optional<Comment> findCommentById(@PathVariable long commentsId) {
//        return this.commentServiceImpl.findCommentById(commentsId);
//    }
//
//    @GetMapping("/product/{productId}")
//    @SecurityRequirement(name = "Authorization")
//    public List<Comment> findAllCommentsByProductId(@PathVariable long productId) {
//        return this.commentServiceImpl.findCommentsByProductId(productId);
//    }
//
//    @GetMapping("/user/{userId}")
//    @SecurityRequirement(name = "Authorization")
//    public List<Comment> findAllCommentsByUserId(@PathVariable long userId) {
//        return this.commentServiceImpl.findCommentsByUserId(userId);
//    }
//
//    @PostMapping("/product/{productId}")
//    @SecurityRequirement(name = "Authorization")
//    public Comment createCommentByProductId(@RequestBody CommentDto commentDto, @PathVariable String productId) {
//        return this.commentServiceImpl.saveCommentByProductId(commentDto, productId);
//    }
//
//    @PutMapping("/product/{productId}")
//    @SecurityRequirement(name = "Authorization")
//    public void editCommentByProductId(@RequestBody Comment comment, @PathVariable long productId) {
//        this.commentServiceImpl.updateCommentByProductId(comment, productId);
//    }
//}
