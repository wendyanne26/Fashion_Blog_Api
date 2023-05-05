package com.wendy.blogapiwithspringsecurity.controllers;

import com.wendy.blogapiwithspringsecurity.dtos.AuthenticationResponse;
import com.wendy.blogapiwithspringsecurity.dtos.PostDto;
import com.wendy.blogapiwithspringsecurity.dtos.UserDto;
import com.wendy.blogapiwithspringsecurity.exceptionHandler.CustomUserException;
import com.wendy.blogapiwithspringsecurity.models.Posts;
import com.wendy.blogapiwithspringsecurity.service.PostService;
import com.wendy.blogapiwithspringsecurity.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
public class AdminController {
    private final UserService userService;
    private final PostService postService;

    @PostMapping("/createPost")
    public ResponseEntity<String> createPost(
            @Valid @RequestBody PostDto postDto,
            Authentication authentication) throws CustomUserException {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        PostDto newPost = postService.createPost(postDto, userDetails.getUsername());
        return new ResponseEntity<>(newPost.getTitle(), HttpStatus.CREATED);

    }
    @PutMapping("/{postId}")
    public ResponseEntity<String> updatePost(@RequestBody PostDto postDto, @PathVariable Long postId){
        postService.updatePost(postDto,postId);
        return  new ResponseEntity<>(postDto.getTitle(), HttpStatus.OK);
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<String> deletePost(@PathVariable Long postId){
        postService.deletePost(postId);
        return new ResponseEntity<>("post deleted", HttpStatus.OK);
    }
}

