package com.apiece.springboot_sns_sample.controller.dto;

import com.apiece.springboot_sns_sample.domain.user.User;

public record FollowResponse(
        Long id,
        String username,
        String nickname
) {
    public static FollowResponse from(User user) {
        return new FollowResponse(
                user.getId(),
                user.getUsername(),
                user.getNickname()
        );
    }
}
