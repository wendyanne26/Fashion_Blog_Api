package com.wendy.blogapiwithspringsecurity.dtos;

import com.wendy.blogapiwithspringsecurity.models.AuditEntity;
import com.wendy.blogapiwithspringsecurity.models.Posts;
import com.wendy.blogapiwithspringsecurity.models.Users;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class CommentDto extends AuditEntity {
    private Long id;
    private String content;
    private Users user;
    private Posts posts;
}
