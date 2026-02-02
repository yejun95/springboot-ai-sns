package com.apiece.springboot_sns_sample.controller;

import com.apiece.springboot_sns_sample.config.auth.AuthUser;
import com.apiece.springboot_sns_sample.controller.dto.PostCreateRequest;
import com.apiece.springboot_sns_sample.controller.dto.PostResponse;
import com.apiece.springboot_sns_sample.controller.dto.PostUpdateRequest;
import com.apiece.springboot_sns_sample.domain.post.Post;
import com.apiece.springboot_sns_sample.domain.post.PostService;
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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping("/api/v1/posts")
    public ResponseEntity<PostResponse> createPost(
            @RequestBody PostCreateRequest request,
            @AuthUser User user) {
        if (user == null) {
            return ResponseEntity.status(401).build();
        }
        Post post = postService.createPost(request.content(), user);
        return ResponseEntity.created(URI.create("/api/v1/posts/" + post.getId()))
                .body(PostResponse.from(post));
    }

    @GetMapping("/api/v1/posts/{id}")
    public ResponseEntity<PostResponse> getPost(@PathVariable Long id) {
        Post post = postService.getPostAndIncrementView(id);
        return ResponseEntity.ok(PostResponse.from(post));
    }

    @GetMapping("/api/v1/posts")
    public ResponseEntity<Page<PostResponse>> getTimelinePosts(
            @PageableDefault(size = 20) Pageable pageable) {
        Page<PostResponse> posts = postService.getTimelinePosts(pageable)
                .map(PostResponse::from);
        return ResponseEntity.ok(posts);
    }

    @GetMapping("/api/v1/posts/user/{userId}")
    public ResponseEntity<Page<PostResponse>> getPostsByUser(
            @PathVariable Long userId,
            @PageableDefault(size = 20) Pageable pageable) {
        Page<PostResponse> posts = postService.getPostsByUser(userId, pageable)
                .map(PostResponse::from);
        return ResponseEntity.ok(posts);
    }

    @PutMapping("/api/v1/posts/{id}")
    public ResponseEntity<PostResponse> updatePost(
            @PathVariable Long id,
            @RequestBody PostUpdateRequest request,
            @AuthUser User user) {
        if (user == null) {
            return ResponseEntity.status(401).build();
        }
        Post post = postService.updatePost(id, request.content(), user.getId());
        return ResponseEntity.ok(PostResponse.from(post));
    }

    @DeleteMapping("/api/v1/posts/{id}")
    public ResponseEntity<Void> deletePost(
            @PathVariable Long id,
            @AuthUser User user) {
        if (user == null) {
            return ResponseEntity.status(401).build();
        }
        postService.deletePost(id, user.getId());
        return ResponseEntity.noContent().build();
    }
}
