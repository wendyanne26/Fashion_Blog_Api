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
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
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
    public Comment createComment(CommentDto commentDto, Long id, Long userId) throws CustomUserException {
        Posts post = postRepository.findById(id).get();
        Users users = userRepository.findById(userId).get();
        if(post != null && users != null ) {
            Comment comment = mapToComment(commentDto);
            comment.setUser(users);
            comment.setPosts(post);
            Comment newComment = commentRepository.save(comment);
            return newComment;
        }
        throw new CustomUserException("post does not exit");
    }

    @Override
    public void deleteComment(Long commentId) {
        commentRepository.deleteById(commentId);
    }

//    public CommentResponse getAllComments(Pageable pageable, Long postId) throws CustomUserException {
//            List<Comment> commentList = commentRepository.findByPostId(postId);
//           Page<Comment> commentPage = new PageImpl<>(commentList, pageable, commentList.size());
//           if(!commentPage.hasContent()){
//               throw new CustomUserException("NO COMMENTS");
//           }
//            List<Comment> commentList1 = commentPage.getContent();
//           List<CommentDto> commentDtoList = commentList1.stream().map(comment -> mapToCommentDto(comment)).collect(Collectors.toList());
//           CommentResponse commentResponse = new CommentResponse();
//           commentResponse.setResponseCommentList(commentDtoList);
//           commentResponse.setLast(commentPage.isLast());
//           commentResponse.setFirst(commentPage.isFirst());
//           commentResponse.setPageNo(commentPage.getNumber());
//           commentResponse.setPageSize(commentPage.getSize());
//           commentResponse.setTotalPages(commentPage.getTotalPages());
//           commentResponse.setTotalElements(commentPage.getNumberOfElements());
//           return commentResponse;
//        }



    public static Comment mapToComment(CommentDto commentDto){

        Comment comment = new Comment();
        comment.setContent(commentDto.getContent());
        comment.setId(commentDto.getId());
        comment.setUser(commentDto.getUser());
        comment.setPosts(commentDto.getPosts());
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