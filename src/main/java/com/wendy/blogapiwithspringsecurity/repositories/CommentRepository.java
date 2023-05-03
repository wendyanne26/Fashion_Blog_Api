package com.wendy.blogapiwithspringsecurity.repositories;

import com.wendy.blogapiwithspringsecurity.models.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    @Query(value = "SELECT * FROM comments c WHERE c.post_id = ?1", nativeQuery = true)
    List<Comment> findByPostId(Long postId);
}
