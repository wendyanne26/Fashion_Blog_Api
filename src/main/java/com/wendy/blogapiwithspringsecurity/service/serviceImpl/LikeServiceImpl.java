package com.wendy.blogapiwithspringsecurity.service.serviceImpl;

import com.wendy.blogapiwithspringsecurity.dtos.LikeDto;
import com.wendy.blogapiwithspringsecurity.exceptionHandler.CustomUserException;
import com.wendy.blogapiwithspringsecurity.models.Like;
import com.wendy.blogapiwithspringsecurity.models.Posts;
import com.wendy.blogapiwithspringsecurity.models.Users;
import com.wendy.blogapiwithspringsecurity.repositories.LikeRepository;
import com.wendy.blogapiwithspringsecurity.repositories.PostRepository;
import com.wendy.blogapiwithspringsecurity.repositories.UserRepository;
import com.wendy.blogapiwithspringsecurity.service.LikeService;
import org.springframework.stereotype.Service;

@Service
public class LikeServiceImpl implements LikeService {
    private final LikeRepository repo;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    public LikeServiceImpl(LikeRepository repo, UserRepository userRepository, PostRepository postRepository) {
        this.repo = repo;
        this.userRepository = userRepository;
        this.postRepository = postRepository;
    }


    @Override
    public Like createLike(LikeDto likeDto, Long postId, Long userId) throws CustomUserException {
        Posts posts = postRepository.findById(postId).get();
        Users users = userRepository.findById(userId).get();
        if(posts != null && users != null){
            Like like = mapToLikeDto(likeDto);
            like.setUser(users);
            like.setPost(posts);
            Like newLike = repo.save(like);
            return newLike;
        }
        throw new CustomUserException("post does not exit");
    }

    @Override
    public void deleteLike(Long likeId) {
        repo.deleteById(likeId);
    }

    private Like mapToLikeDto(LikeDto likeDto) {
        Like like = new Like();
        like.setPost(likeDto.getPost());
        like.setId(likeDto.getId());
        like.setUser(likeDto.getUser());
        return like;
    }
}

