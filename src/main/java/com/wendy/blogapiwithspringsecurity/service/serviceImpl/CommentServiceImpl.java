package com.wendy.blogapiwithspringsecurity.service.serviceImpl;

import com.wendy.blogapiwithspringsecurity.dtos.CommentDto;
import com.wendy.blogapiwithspringsecurity.exceptionHandler.CustomUserException;
import com.wendy.blogapiwithspringsecurity.models.Comment;
import com.wendy.blogapiwithspringsecurity.models.Posts;
import com.wendy.blogapiwithspringsecurity.models.Users;
import com.wendy.blogapiwithspringsecurity.repositories.CommentRepository;
import com.wendy.blogapiwithspringsecurity.repositories.PostRepository;
import com.wendy.blogapiwithspringsecurity.repositories.UserRepository;
import com.wendy.blogapiwithspringsecurity.service.CommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Slf4j
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public CommentServiceImpl(CommentRepository commentRepository, PostRepository postRepository, UserRepository userRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Comment createComment(CommentDto commentDto, Long id, String request) throws CustomUserException {
        Comment comment = mapToComment(commentDto);
        Posts post = postRepository.findById(id).get();
        Users users = userRepository.findByEmail(request)
                .orElseThrow();
            comment.setUser(users);
            comment.setPosts(post);
            comment.setCreatedAt(LocalDateTime.now());
            comment.setUpdatedAt(LocalDateTime.now());
            Comment newComment = commentRepository.save(comment);
            return newComment;

    }

    @Override
    public void deleteComment(Long commentId) {
        commentRepository.deleteById(commentId);
    }


    public static Comment mapToComment(CommentDto commentDto){

        Comment comment = new Comment();
        comment.setContent(commentDto.getContent());
        comment.setId(commentDto.getId());
        comment.setUser(commentDto.getUser());
        comment.setPosts(commentDto.getPosts());
        comment.setContent(commentDto.getContent());
        comment.setCreatedAt(LocalDateTime.now());
        comment.setUpdatedAt(LocalDateTime.now());
        return comment;
    }
    private CommentDto mapToCommentDto(Comment comment){
        CommentDto commentDto = new CommentDto();
        commentDto.setPosts(comment.getPosts());
        commentDto.setContent(comment.getContent());
        commentDto.setId(comment.getId());
        commentDto.setUser(comment.getUser());
        commentDto.setUpdatedAt(comment.getUpdatedAt());
        commentDto.setCreatedAt(comment.getCreatedAt());
        return commentDto;
    }
}