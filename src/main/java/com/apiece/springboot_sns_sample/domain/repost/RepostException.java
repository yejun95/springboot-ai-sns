package com.apiece.springboot_sns_sample.domain.repost;

public class RepostException extends RuntimeException {

    public RepostException(String message) {
        super(message);
    }

    public static class NotRepostException extends RepostException {
        public NotRepostException() {
            super("리포스트가 아닙니다.");
        }
    }
}
