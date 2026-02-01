package com.apiece.springboot_sns_sample.controller;

import com.apiece.springboot_sns_sample.config.auth.AuthUser;
import com.apiece.springboot_sns_sample.controller.dto.FollowCountResponse;
import com.apiece.springboot_sns_sample.controller.dto.FollowResponse;
import com.apiece.springboot_sns_sample.controller.dto.FollowerListResponse;
import com.apiece.springboot_sns_sample.domain.follow.FollowService;
import com.apiece.springboot_sns_sample.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class FollowController {

    private final FollowService followService;

    @PostMapping("/api/v1/follow/{targetId}")
    public ResponseEntity<Void> follow(@AuthUser User user, @PathVariable Long targetId) {
        if (user == null) {
            return ResponseEntity.status(401).build();
        }
        followService.follow(user.getId(), targetId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/api/v1/follow/{targetId}")
    public ResponseEntity<Void> unfollow(@AuthUser User user, @PathVariable Long targetId) {
        if (user == null) {
            return ResponseEntity.status(401).build();
        }
        followService.unfollow(user.getId(), targetId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/api/v1/follow/followers")
    public ResponseEntity<FollowerListResponse> getFollowers(
            @AuthUser User user,
            @PageableDefault(size = 20) Pageable pageable) {
        if (user == null) {
            return ResponseEntity.status(401).build();
        }
        Page<FollowResponse> followers = followService.getFollowers(user.getId(), pageable)
                .map(FollowResponse::from);
        return ResponseEntity.ok(FollowerListResponse.from(followers));
    }

    @GetMapping("/api/v1/follow/followees")
    public ResponseEntity<FollowerListResponse> getFollowees(
            @AuthUser User user,
            @PageableDefault(size = 20) Pageable pageable) {
        if (user == null) {
            return ResponseEntity.status(401).build();
        }
        Page<FollowResponse> followees = followService.getFollowees(user.getId(), pageable)
                .map(FollowResponse::from);
        return ResponseEntity.ok(FollowerListResponse.from(followees));
    }

    @GetMapping("/api/v1/follow/count")
    public ResponseEntity<FollowCountResponse> getFollowCount(@AuthUser User user) {
        if (user == null) {
            return ResponseEntity.status(401).build();
        }
        return ResponseEntity.ok(FollowCountResponse.from(followService.getFollowCount(user.getId())));
    }
}
