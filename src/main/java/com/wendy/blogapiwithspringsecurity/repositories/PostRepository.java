package com.wendy.blogapiwithspringsecurity.repositories;

import com.wendy.blogapiwithspringsecurity.models.Posts;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Posts, Long> {
    @Modifying
    @Transactional
    @Query(value = "UPDATE posts SET content = ?1 WHERE id = ?2", nativeQuery = true)
    void updatePost(String content, Long id);

    @Query(value = "SELECT * FROM posts p WHERE p.title LIKE %?1%", nativeQuery = true)
    List<Posts> searchByTitle(String title );

    Optional<Posts> findById(Long postId);


    Page<Posts> findAll(Pageable pageable);

}
