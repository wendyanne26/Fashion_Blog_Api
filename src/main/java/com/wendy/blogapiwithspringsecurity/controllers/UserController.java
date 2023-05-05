package com.wendy.blogapiwithspringsecurity.controllers;

import com.wendy.blogapiwithspringsecurity.dtos.AuthenticationResponse;
import com.wendy.blogapiwithspringsecurity.dtos.UserDto;
import com.wendy.blogapiwithspringsecurity.exceptionHandler.CustomUserException;
import com.wendy.blogapiwithspringsecurity.models.Users;
import com.wendy.blogapiwithspringsecurity.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserService userService;
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }
    @PostMapping("/register")
    public AuthenticationResponse createUser(@RequestBody UserDto userDto){
        return userService.createUser(userDto);
    }
    @GetMapping("/{id}")
    public Users getUser(@PathVariable Long id){
        Users user = userService.getUser(id);
        return user;
    }
    @PostMapping("/login")
    public AuthenticationResponse loginUser(@RequestBody UserDto userDto)throws CustomUserException {
      return userService.loginUser(userDto);
    }
    @PostMapping("/register/Admin")
    public AuthenticationResponse createAdmin(@RequestBody UserDto userDto){
        return userService.createAdmin(userDto);

    }

}
