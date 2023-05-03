package com.wendy.blogapiwithspringsecurity.dtos;

import com.wendy.blogapiwithspringsecurity.models.AuditEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class PostDto extends AuditEntity {
    private Long id;
    private String title;
    private String content;
    private String category;



}

