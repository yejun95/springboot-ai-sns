package com.apiece.springboot_sns_sample.controller;

import com.apiece.springboot_sns_sample.config.auth.AuthUser;
import com.apiece.springboot_sns_sample.controller.dto.PostResponse;
import com.apiece.springboot_sns_sample.controller.dto.PostUpdateRequest;
import com.apiece.springboot_sns_sample.controller.dto.ReplyCreateRequest;
import com.apiece.springboot_sns_sample.domain.post.Post;
import com.apiece.springboot_sns_sample.domain.reply.ReplyService;
import com.apiece.springboot_sns_sample.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ReplyController {

    private final ReplyService replyService;

    @PostMapping("/api/v1/replies")
    public ResponseEntity<PostResponse> createReply(
            @RequestBody ReplyCreateRequest request,
            @AuthUser User user) {
        if (user == null) {
            return ResponseEntity.status(401).build();
        }
        Post reply = replyService.createReply(request.parentId(), request.content(), user);
        return ResponseEntity.created(URI.create("/api/v1/replies/" + reply.getId()))
                .body(PostResponse.from(reply));
    }

    @GetMapping("/api/v1/replies/post/{postId}")
    public ResponseEntity<List<PostResponse>> getRepliesByPostId(@PathVariable Long postId) {
        List<PostResponse> replies = replyService.getRepliesByPostId(postId)
                .stream()
                .map(PostResponse::from)
                .toList();
        return ResponseEntity.ok(replies);
    }

    @PutMapping("/api/v1/replies/{id}")
    public ResponseEntity<PostResponse> updateReply(
            @PathVariable Long id,
            @RequestBody PostUpdateRequest request,
            @AuthUser User user) {
        if (user == null) {
            return ResponseEntity.status(401).build();
        }
        Post reply = replyService.updateReply(id, request.content(), user.getId());
        return ResponseEntity.ok(PostResponse.from(reply));
    }

    @DeleteMapping("/api/v1/replies/{id}")
    public ResponseEntity<Void> deleteReply(
            @PathVariable Long id,
            @AuthUser User user) {
        if (user == null) {
            return ResponseEntity.status(401).build();
        }
        replyService.deleteReply(id, user.getId());
        return ResponseEntity.noContent().build();
    }
}
