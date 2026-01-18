package com.apiece.springboot_sns_sample.domain.user;

public class UserException extends RuntimeException {

    public UserException(String message) {
        super(message);
    }

    public static UserException notFound(Long id) {
        return new UserException("User not found with id: " + id);
    }

    public static UserException emailAlreadyExists(String email) {
        return new UserException("Email already exists: " + email);
    }
}
