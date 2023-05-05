package com.wendy.blogapiwithspringsecurity.service;

import com.wendy.blogapiwithspringsecurity.dtos.LikeDto;
import com.wendy.blogapiwithspringsecurity.exceptionHandler.CustomUserException;
import com.wendy.blogapiwithspringsecurity.models.Like;

public interface LikeService {

    Like createLike(Long postId, String request) throws CustomUserException;

    void deleteLike(Long likeId);
}
