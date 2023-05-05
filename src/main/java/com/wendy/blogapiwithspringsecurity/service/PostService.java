package com.wendy.blogapiwithspringsecurity.service;

import com.wendy.blogapiwithspringsecurity.dtos.PostDto;
import com.wendy.blogapiwithspringsecurity.dtos.PostResponse;
import com.wendy.blogapiwithspringsecurity.exceptionHandler.CustomUserException;
import com.wendy.blogapiwithspringsecurity.models.Posts;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PostService {
    PostDto createPost(PostDto postDto, String request) throws CustomUserException;


    void updatePost(PostDto postDto, Long postId);

    List<PostDto> searchPost(String title) throws CustomUserException;

    void deletePost(Long postId);

    PostResponse getAllPosts(Pageable pageable) throws CustomUserException;


}
