package com.apiece.springboot_sns_sample.domain.reply;

public class ReplyException extends RuntimeException {

    public ReplyException(String message) {
        super(message);
    }

    public static class NotReplyException extends ReplyException {
        public NotReplyException() {
            super("답글이 아닙니다.");
        }
    }
}
