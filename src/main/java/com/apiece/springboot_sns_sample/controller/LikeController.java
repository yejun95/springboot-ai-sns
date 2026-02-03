package com.apiece.springboot_sns_sample.controller;

import com.apiece.springboot_sns_sample.config.auth.AuthUser;
import com.apiece.springboot_sns_sample.controller.dto.LikeResponse;
import com.apiece.springboot_sns_sample.domain.like.Like;
import com.apiece.springboot_sns_sample.domain.like.LikeService;
import com.apiece.springboot_sns_sample.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LikeController {

    private final LikeService likeService;

    @PostMapping("/api/v1/posts/{postId}/like")
    public ResponseEntity<LikeResponse> likePost(@AuthUser User user, @PathVariable Long postId) {
        if (user == null) {
            return ResponseEntity.status(401).build();
        }
        Like like = likeService.likePost(user, postId);
        return ResponseEntity.ok(LikeResponse.from(like));
    }

    @DeleteMapping("/api/v1/posts/{postId}/like")
    public ResponseEntity<Void> unlikePost(@AuthUser User user, @PathVariable Long postId) {
        if (user == null) {
            return ResponseEntity.status(401).build();
        }
        likeService.unlikePost(user.getId(), postId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/api/v1/posts/{postId}/like/check")
    public ResponseEntity<Boolean> isLiked(@AuthUser User user, @PathVariable Long postId) {
        if (user == null) {
            return ResponseEntity.status(401).build();
        }
        boolean isLiked = likeService.isLiked(user.getId(), postId);
        return ResponseEntity.ok(isLiked);
    }
}
