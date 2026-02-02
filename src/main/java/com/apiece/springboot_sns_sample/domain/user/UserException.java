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

    public static class UserNotFoundException extends UserException {
        public UserNotFoundException() {
            super("사용자를 찾을 수 없습니다.");
        }
    }

    public static class DuplicateUsernameException extends UserException {
        public DuplicateUsernameException() {
            super("이미 존재하는 사용자명입니다.");
        }
    }
}
