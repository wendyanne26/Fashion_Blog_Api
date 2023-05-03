package com.wendy.blogapiwithspringsecurity.controllers;

import com.wendy.blogapiwithspringsecurity.dtos.PostDto;
import com.wendy.blogapiwithspringsecurity.dtos.PostResponse;
import com.wendy.blogapiwithspringsecurity.exceptionHandler.CustomUserException;
import com.wendy.blogapiwithspringsecurity.models.Posts;
import com.wendy.blogapiwithspringsecurity.service.PostService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping( "/api/v1/posts")
public class PostController {
    private final PostService postService;
    public PostController(PostService postService) {
        this.postService = postService;
    }
    @PostMapping
    public ResponseEntity<String> createPost(
            @RequestBody PostDto postDto,
            HttpServletRequest servletRequest)
    {
        try {
            HttpSession session = servletRequest.getSession();
            Long id =(Long)session.getAttribute("userId");
            Posts newPost = postService.createPost(postDto, id);
            return new ResponseEntity<>(newPost.getTitle(), HttpStatus.CREATED);
        } catch (CustomUserException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
    @PutMapping("/{postId}")
    public ResponseEntity<String> updatePost(@RequestBody PostDto postDto, @PathVariable Long postId){
        postService.updatePost(postDto,postId);
        return  new ResponseEntity<>(postDto.getTitle(), HttpStatus.OK);

    }
    @PostMapping("/{title}")
    public ResponseEntity<List<PostDto>> searchPost(@PathVariable String title) throws CustomUserException {
        List<PostDto>result = postService.searchPost(title);
        return  new ResponseEntity<>(result, HttpStatus.OK);
    }
    @DeleteMapping("/{postId}")
    public ResponseEntity<String> deletePost(@PathVariable Long postId){
        postService.deletePost(postId);
        return new ResponseEntity<>("post deleted", HttpStatus.OK);
    }

    @GetMapping("/viewPosts")
    public ResponseEntity<PostResponse> getAllPosts(@RequestParam (defaultValue = "1") int pageNo,
                                                    @RequestParam(defaultValue = "5") int pageSize) throws CustomUserException {
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
        PostResponse postResponse = postService.getAllPosts(pageable);
        return  ResponseEntity.ok(postResponse);
    }
}

