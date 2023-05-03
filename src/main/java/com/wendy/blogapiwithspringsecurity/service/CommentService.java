package com.wendy.blogapiwithspringsecurity.service;

import com.wendy.blogapiwithspringsecurity.dtos.CommentDto;
import com.wendy.blogapiwithspringsecurity.exceptionHandler.CustomUserException;
import com.wendy.blogapiwithspringsecurity.models.Comment;

public interface CommentService {
    Comment createComment(CommentDto commentDto, Long id, Long userId) throws CustomUserException;

    void deleteComment(Long commentId);

    //CommentResponse getAllComments(Pageable pageable, Long postId) throws CustomUserException;
}