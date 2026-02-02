package com.apiece.springboot_sns_sample.domain.post;

public class PostException extends RuntimeException {

    public PostException(String message) {
        super(message);
    }

    public static class PostNotFoundException extends PostException {
        public PostNotFoundException() {
            super("게시글을 찾을 수 없습니다.");
        }
    }

    public static class UnauthorizedException extends PostException {
        public UnauthorizedException() {
            super("게시글 작성자만 수정/삭제할 수 있습니다.");
        }
    }
}
