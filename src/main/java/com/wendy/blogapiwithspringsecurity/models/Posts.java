package com.wendy.blogapiwithspringsecurity.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor

@NoArgsConstructor
@Data
@Table(name = "posts")
@Builder
@EntityListeners(AuditingEntityListener.class)
public class Posts extends AuditEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String Title;
    @Column(nullable = false, length = 1000)
    private String content;
    @Column(nullable = false)
    private String category;
    @OneToMany(mappedBy = "posts", fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();


}
