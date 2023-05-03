package com.wendy.blogapiwithspringsecurity.exceptionHandler;

public class CustomUserException extends Exception{
    public CustomUserException() {
    }
    public CustomUserException(String message) {
        super(message);
    }

}
