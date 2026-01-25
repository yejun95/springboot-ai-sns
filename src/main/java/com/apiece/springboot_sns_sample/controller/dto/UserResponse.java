package com.apiece.springboot_sns_sample.controller.dto;

import com.apiece.springboot_sns_sample.domain.user.User;

public record UserResponse(Long id, String username, String email, String nickname, String bio) {

    public static UserResponse from(User user) {
        return new UserResponse(user.getId(), user.getUsername(), user.getEmail(), user.getNickname(), user.getBio());
    }
}
