package com.wendy.blogapiwithspringsecurity.dtos;

import com.wendy.blogapiwithspringsecurity.enums.Roles;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class UserDto {
    private Long id;
    @NonNull
    private String username;
    private String password;
    @NonNull
    private String email;
    @Enumerated(EnumType.STRING)
    private Roles role;
}
