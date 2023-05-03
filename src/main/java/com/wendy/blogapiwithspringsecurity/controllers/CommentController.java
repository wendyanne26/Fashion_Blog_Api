package com.wendy.blogapiwithspringsecurity.controllers;

import com.wendy.blogapiwithspringsecurity.dtos.CommentDto;
import com.wendy.blogapiwithspringsecurity.exceptionHandler.CustomUserException;
import com.wendy.blogapiwithspringsecurity.models.Comment;
import com.wendy.blogapiwithspringsecurity.service.CommentService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/comments")
@Slf4j
public class CommentController {
    private final CommentService service;

    public CommentController(CommentService service) {
        this.service = service;
    }

    @PostMapping("/{postId}")
    public ResponseEntity<String> createComment(@PathVariable Long postId, @RequestBody CommentDto commentDto, HttpServletRequest request) throws CustomUserException {
        HttpSession session = request.getSession();
        Long userId = (Long) session.getAttribute("userId");
        log.info("this is the user ----->:" + userId);
        log.info("this is the post ----->:" + postId);
        Comment newComment = service.createComment(commentDto, postId, userId);
        HttpSession session1 = request.getSession();
        session1.setAttribute("commentId", commentDto.getId());
        return new ResponseEntity<>(newComment.getContent(), HttpStatus.CREATED);
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<String> deleteComment(@PathVariable Long commentId) {
        service.deleteComment(commentId);
        return new ResponseEntity<>("comment deleted", HttpStatus.OK);
    }
}
