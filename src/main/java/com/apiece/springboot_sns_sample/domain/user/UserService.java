package com.apiece.springboot_sns_sample.domain.user;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User register(String email, String password, String nickname) {
        if (userRepository.existsByEmail(email)) {
            throw UserException.emailAlreadyExists(email);
        }
        User user = new User(email, password, nickname);
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
