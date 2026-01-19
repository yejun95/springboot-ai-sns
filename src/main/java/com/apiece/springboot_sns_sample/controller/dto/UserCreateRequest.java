package com.apiece.springboot_sns_sample.controller.dto;

import com.apiece.springboot_sns_sample.domain.user.User;

public record UserCreateRequest(String username, String password, String nickname) {

    public User toEntity() {
        return new User(username, password);
    }
}
