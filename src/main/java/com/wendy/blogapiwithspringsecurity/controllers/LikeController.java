package com.wendy.blogapiwithspringsecurity.controllers;

import com.wendy.blogapiwithspringsecurity.dtos.LikeDto;
import com.wendy.blogapiwithspringsecurity.exceptionHandler.CustomUserException;
import com.wendy.blogapiwithspringsecurity.service.LikeService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping( "api/v1/likes")
public class LikeController {
    private final LikeService likeService;

    public LikeController(LikeService likeService) {
        this.likeService = likeService;
    }
    @PostMapping("/{postId}")
    public ResponseEntity<String> createLike(
            @PathVariable Long postId,
            Authentication authentication
    ) throws CustomUserException {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        likeService.createLike(postId,userDetails.getUsername());

        return new ResponseEntity<>("liked", HttpStatus.CREATED);
    }
    @DeleteMapping("/{likeId}")
    public ResponseEntity<String> deleteLike(@PathVariable Long likeId){
        likeService.deleteLike(likeId);
        return new ResponseEntity<>("deleted", HttpStatus.OK);
    }
}
