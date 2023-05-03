package com.wendy.blogapiwithspringsecurity.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Table(name = "likes")
@EntityListeners(AuditingEntityListener.class)
public class Like extends AuditEntity {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id;
    private boolean liked = false;
    @ManyToOne
    private Posts post;
    @ManyToOne()
    @JoinColumn(name = "user_id", nullable = false)
    private Users user;

}

