package com.apiece.springboot_sns_sample.controller;

import com.apiece.springboot_sns_sample.controller.dto.UserCreateRequest;
import com.apiece.springboot_sns_sample.controller.dto.UserResponse;
import com.apiece.springboot_sns_sample.controller.dto.UserUpdateRequest;
import com.apiece.springboot_sns_sample.domain.user.User;
import com.apiece.springboot_sns_sample.domain.user.UserService;
import java.net.URI;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/api/users")
    public ResponseEntity<UserResponse> register(@RequestBody UserCreateRequest request) {
        User user = userService.register(request.email(), request.password(), request.nickname());
        return ResponseEntity.created(URI.create("/api/users/" + user.getId()))
                .body(UserResponse.from(user));
    }

    @GetMapping("/api/users/{id}")
    public ResponseEntity<UserResponse> getUser(@PathVariable Long id) {
        User user = userService.getById(id);
        return ResponseEntity.ok(UserResponse.from(user));
    }

    @GetMapping("/api/users")
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        List<UserResponse> users =
                userService.getAll().stream().map(UserResponse::from).toList();
        return ResponseEntity.ok(users);
    }

    @PutMapping("/api/users/{id}")
    public ResponseEntity<UserResponse> updateProfile(
            @PathVariable Long id, @RequestBody UserUpdateRequest request) {
        User user = userService.updateProfile(id, request.nickname(), request.bio());
        return ResponseEntity.ok(UserResponse.from(user));
    }

    @DeleteMapping("/api/users/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
