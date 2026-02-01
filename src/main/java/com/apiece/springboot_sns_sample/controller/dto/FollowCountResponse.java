package com.apiece.springboot_sns_sample.controller.dto;

import com.apiece.springboot_sns_sample.domain.follow.FollowCount;

public record FollowCountResponse(
        Long followersCount,
        Long followeesCount
) {
    public static FollowCountResponse from(FollowCount followCount) {
        return new FollowCountResponse(
                followCount.getFollowersCount(),
                followCount.getFolloweesCount()
        );
    }
}
