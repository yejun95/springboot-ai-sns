package com.apiece.springboot_sns_sample.domain.follow;

public class FollowException extends RuntimeException {

    public FollowException(String message) {
        super(message);
    }

    public static FollowException cannotFollowSelf() {
        return new FollowException("자기 자신을 팔로우할 수 없습니다.");
    }

    public static FollowException alreadyFollowing() {
        return new FollowException("이미 팔로우한 사용자입니다.");
    }

    public static FollowException notFollowing() {
        return new FollowException("팔로우하지 않은 사용자입니다.");
    }

    public static FollowException userNotFound() {
        return new FollowException("사용자를 찾을 수 없습니다.");
    }
}
