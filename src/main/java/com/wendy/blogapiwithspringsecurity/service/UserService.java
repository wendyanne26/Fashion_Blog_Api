package com.wendy.blogapiwithspringsecurity.service;

import com.wendy.blogapiwithspringsecurity.dtos.AuthenticationResponse;
import com.wendy.blogapiwithspringsecurity.dtos.UserDto;
import com.wendy.blogapiwithspringsecurity.exceptionHandler.CustomUserException;
import com.wendy.blogapiwithspringsecurity.models.Users;

public interface UserService {
    Users getUser(Long id);

    AuthenticationResponse createUser(UserDto userDto);


    AuthenticationResponse loginUser(UserDto userDto) throws CustomUserException;

    AuthenticationResponse createAdmin(UserDto userDto);
}