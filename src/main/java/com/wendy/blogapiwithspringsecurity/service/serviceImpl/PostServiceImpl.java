package com.wendy.blogapiwithspringsecurity.service.serviceImpl;

import com.wendy.blogapiwithspringsecurity.dtos.PostDto;
import com.wendy.blogapiwithspringsecurity.dtos.PostResponse;
import com.wendy.blogapiwithspringsecurity.enums.Roles;
import com.wendy.blogapiwithspringsecurity.exceptionHandler.CustomUserException;
import com.wendy.blogapiwithspringsecurity.models.Posts;
import com.wendy.blogapiwithspringsecurity.models.Users;
import com.wendy.blogapiwithspringsecurity.repositories.PostRepository;
import com.wendy.blogapiwithspringsecurity.repositories.UserRepository;
import com.wendy.blogapiwithspringsecurity.service.PostService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public PostServiceImpl(PostRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Posts createPost(PostDto postDto, Long id) throws CustomUserException {
        log.info("this is the admin session ID: --------->" + id);
        Users users = getCurrentUser(id);
        if(users.getRole().equals(Roles.ADMIN)) {
            Posts posts = mapToPosts(postDto);
            Posts newPost = postRepository.save(posts);
            return newPost;
        }else{
            throw new CustomUserException("Only an Admin can perform this function");
        }
    }

    @Override
    public void updatePost(PostDto postDto, Long postId) {
        postRepository.updatePost(postDto.getContent(), postId);
    }

    @Override
    public List<PostDto> searchPost(String title) throws CustomUserException {
        List<Posts> postsList  = postRepository.searchByTitle(title);
        List<PostDto> searchResults = new ArrayList<>();
        if(postsList.isEmpty()){
            throw new CustomUserException("post doesn't exist");
        }else {
            for (Posts eachPost : postsList) {
                PostDto p = mapToPostDto(eachPost);
                searchResults.add(p);
            }
        }
        return searchResults;
    }

    @Override
    public void deletePost(Long postId) {
        postRepository.deleteById(postId);
    }

    @Override
    public PostResponse getAllPosts(Pageable pageable) throws CustomUserException {
        Page<Posts> page = postRepository.findAll(pageable);
        if(!page.hasContent()){
            throw new CustomUserException("NO POSTS");
        }
        List<Posts> postsList = page.getContent();
        List<PostDto> postDtoList = postsList.stream().map(posts -> mapToPostDto(posts)).collect(Collectors.toList());
        PostResponse postResponse = new PostResponse();
        postResponse.setResponsePost(postDtoList);
        postResponse.setLast(page.isLast());
        postResponse.setFirst(page.isFirst());
        postResponse.setPageNo(page.getNumber());
        postResponse.setPageSize(page.getSize());
        postResponse.setTotalItems(page.getNumberOfElements());
        postResponse.setTotalPages(page.getTotalPages());
        return postResponse;
    }

    private Users getCurrentUser(Long id) {
        Optional< Users> user = userRepository.findById(id);
        return user.get();
    }

    private Posts mapToPosts(PostDto posts){
        Posts posts1 = new Posts();
        posts1.setCategory(posts.getCategory());
        posts1.setId(posts.getId());
        posts1.setTitle(posts.getTitle());
        posts1.setContent(posts.getContent());
        posts1.setCreatedAt(LocalDateTime.now());
        posts1.setUpdatedAt(LocalDateTime.now());
        return posts1;
    }
    private PostDto mapToPostDto(Posts post1s){
        PostDto postDto = new PostDto();
        postDto.setCategory(post1s.getCategory());
        postDto.setContent(post1s.getContent());
        postDto.setTitle(post1s.getTitle());
        postDto.setId(post1s.getId());
        postDto.setUpdatedAt(post1s.getUpdatedAt());
        postDto.setCreatedAt(post1s.getCreatedAt());
        return postDto;
    }

}




