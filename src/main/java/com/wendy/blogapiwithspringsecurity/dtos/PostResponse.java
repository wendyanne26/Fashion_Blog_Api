package com.wendy.blogapiwithspringsecurity.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class PostResponse {
    private List<PostDto> responsePost;
    private int pageSize;
    private int pageNo;
    private int totalItems;
    private int totalPages;
    private boolean isFirst;
    private boolean isLast;
}
