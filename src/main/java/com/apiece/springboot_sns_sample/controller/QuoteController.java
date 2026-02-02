package com.apiece.springboot_sns_sample.controller;

import com.apiece.springboot_sns_sample.config.auth.AuthUser;
import com.apiece.springboot_sns_sample.controller.dto.PostResponse;
import com.apiece.springboot_sns_sample.controller.dto.PostUpdateRequest;
import com.apiece.springboot_sns_sample.controller.dto.QuoteCreateRequest;
import com.apiece.springboot_sns_sample.domain.post.Post;
import com.apiece.springboot_sns_sample.domain.quote.QuoteService;
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

@RestController
@RequiredArgsConstructor
public class QuoteController {

    private final QuoteService quoteService;

    @PostMapping("/api/v1/quotes")
    public ResponseEntity<PostResponse> createQuote(
            @RequestBody QuoteCreateRequest request,
            @AuthUser User user) {
        if (user == null) {
            return ResponseEntity.status(401).build();
        }
        Post quote = quoteService.createQuote(request.quoteId(), request.content(), user);
        return ResponseEntity.created(URI.create("/api/v1/quotes/" + quote.getId()))
                .body(PostResponse.from(quote));
    }

    @GetMapping("/api/v1/quotes/{id}")
    public ResponseEntity<PostResponse> getQuote(@PathVariable Long id) {
        Post quote = quoteService.getQuote(id);
        return ResponseEntity.ok(PostResponse.from(quote));
    }

    @PutMapping("/api/v1/quotes/{id}")
    public ResponseEntity<PostResponse> updateQuote(
            @PathVariable Long id,
            @RequestBody PostUpdateRequest request,
            @AuthUser User user) {
        if (user == null) {
            return ResponseEntity.status(401).build();
        }
        Post quote = quoteService.updateQuote(id, request.content(), user.getId());
        return ResponseEntity.ok(PostResponse.from(quote));
    }

    @DeleteMapping("/api/v1/quotes/{id}")
    public ResponseEntity<Void> deleteQuote(
            @PathVariable Long id,
            @AuthUser User user) {
        if (user == null) {
            return ResponseEntity.status(401).build();
        }
        quoteService.deleteQuote(id, user.getId());
        return ResponseEntity.noContent().build();
    }
}
