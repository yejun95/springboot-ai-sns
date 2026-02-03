package com.apiece.springboot_sns_sample.controller.dto;

import com.apiece.springboot_sns_sample.domain.like.Like;

public record LikeResponse(
        Long id,
        Long userId,
        Long postId
) {
    public static LikeResponse from(Like like) {
        return new LikeResponse(
                like.getId(),
                like.getUser().getId(),
                like.getPost().getId()
        );
    }
}
