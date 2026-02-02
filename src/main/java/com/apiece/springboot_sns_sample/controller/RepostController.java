package com.apiece.springboot_sns_sample.controller;

import com.apiece.springboot_sns_sample.config.auth.AuthUser;
import com.apiece.springboot_sns_sample.controller.dto.PostResponse;
import com.apiece.springboot_sns_sample.controller.dto.RepostCreateRequest;
import com.apiece.springboot_sns_sample.domain.post.Post;
import com.apiece.springboot_sns_sample.domain.repost.RepostService;
import com.apiece.springboot_sns_sample.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequiredArgsConstructor
public class RepostController {

    private final RepostService repostService;

    @PostMapping("/api/v1/reposts")
    public ResponseEntity<PostResponse> createRepost(
            @RequestBody RepostCreateRequest request,
            @AuthUser User user) {
        if (user == null) {
            return ResponseEntity.status(401).build();
        }
        Post repost = repostService.createRepost(request.repostId(), user);
        return ResponseEntity.created(URI.create("/api/v1/reposts/" + repost.getId()))
                .body(PostResponse.from(repost));
    }

    @GetMapping("/api/v1/reposts/{id}")
    public ResponseEntity<PostResponse> getRepost(@PathVariable Long id) {
        Post repost = repostService.getRepost(id);
        return ResponseEntity.ok(PostResponse.from(repost));
    }

    @DeleteMapping("/api/v1/reposts/{id}")
    public ResponseEntity<Void> deleteRepost(
            @PathVariable Long id,
            @AuthUser User user) {
        if (user == null) {
            return ResponseEntity.status(401).build();
        }
        repostService.deleteRepost(id, user.getId());
        return ResponseEntity.noContent().build();
    }
}
