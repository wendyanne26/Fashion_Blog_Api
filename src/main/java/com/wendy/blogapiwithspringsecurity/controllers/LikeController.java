package com.wendy.blogapiwithspringsecurity.controllers;

import com.wendy.blogapiwithspringsecurity.dtos.LikeDto;
import com.wendy.blogapiwithspringsecurity.exceptionHandler.CustomUserException;
import com.wendy.blogapiwithspringsecurity.service.LikeService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping( "api/v1/likes")
public class LikeController {
    private final LikeService likeService;

    public LikeController(LikeService likeService) {
        this.likeService = likeService;
    }
    @PostMapping("/{postId}")
    public ResponseEntity<String> createLike(@PathVariable Long postId, @RequestBody LikeDto likeDto, HttpServletRequest servletRequest) throws CustomUserException {
        HttpSession session = servletRequest.getSession();
        Long userId = (Long) session.getAttribute("userId");
        likeService.createLike(likeDto,postId, userId);
        HttpSession session1 = servletRequest.getSession();
        session1.setAttribute("likeId", likeDto.getId());
        return new ResponseEntity<>("liked", HttpStatus.CREATED);
    }
    @DeleteMapping("/{likeId}")
    public ResponseEntity<String> deleteLike(@PathVariable Long likeId){
        likeService.deleteLike(likeId);
        return new ResponseEntity<>("deleted", HttpStatus.OK);
    }
}
