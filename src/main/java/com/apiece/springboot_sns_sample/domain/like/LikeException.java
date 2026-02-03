package com.apiece.springboot_sns_sample.domain.like;

public class LikeException extends RuntimeException {

    public LikeException(String message) {
        super(message);
    }

    public static LikeException alreadyLiked() {
        return new LikeException("이미 좋아요를 눌렀습니다.");
    }

    public static LikeException notFound() {
        return new LikeException("좋아요를 찾을 수 없습니다.");
    }
}
