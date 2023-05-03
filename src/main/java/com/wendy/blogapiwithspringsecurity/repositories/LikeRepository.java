package com.wendy.blogapiwithspringsecurity.repositories;

import com.wendy.blogapiwithspringsecurity.models.Like;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<Like, Long> {
}
