package com.apiece.springboot_sns_sample.domain.user;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User register(String email, String username, String password, String nickname) {
        if (userRepository.existsByEmail(email)) {
            throw UserException.emailAlreadyExists(email);
        }
        User user = new User(email, username, password, nickname);
        return userRepository.save(user);
    }

    public User signup(String username, String password) {
        if (userRepository.existsByUsername(username)) {
            throw new UserException("Username already exists: " + username);
        }
        String encodedPassword = passwordEncoder.encode(password);
        User user = new User(username, encodedPassword);
        return userRepository.save(user);
    }

    public User getById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> UserException.notFound(id));
    }

    public User getByEmail(String email) {
        return userRepository
                .findByEmail(email)
                .orElseThrow(() -> new UserException("User not found with email: " + email));
    }

    public User getByUsername(String username) {
        return userRepository
                .findByUsername(username)
                .orElseThrow(() -> new UserException("User not found with username: " + username));
    }

    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Transactional
    public User updateProfile(Long id, String nickname, String bio) {
        User user = getById(id);
        user.updateProfile(nickname, bio);
        return user;
    }

    public void delete(Long id) {
        if (!userRepository.existsById(id)) {
            throw UserException.notFound(id);
        }
        userRepository.deleteById(id);
    }
}
