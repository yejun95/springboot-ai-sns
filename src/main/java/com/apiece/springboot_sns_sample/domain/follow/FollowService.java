package com.apiece.springboot_sns_sample.domain.follow;

import com.apiece.springboot_sns_sample.domain.user.User;
import com.apiece.springboot_sns_sample.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FollowService {

    private final FollowRepository followRepository;
    private final FollowCountRepository followCountRepository;
    private final UserRepository userRepository;

    @Transactional
    public void follow(Long followerId, Long followingId) {
        if (followerId.equals(followingId)) {
            throw FollowException.cannotFollowSelf();
        }

        User follower = userRepository.findById(followerId)
                .orElseThrow(FollowException::userNotFound);
        User following = userRepository.findById(followingId)
                .orElseThrow(FollowException::userNotFound);

        if (followRepository.existsByFollowerAndFollowing(follower, following)) {
            throw FollowException.alreadyFollowing();
        }

        followRepository.save(new Follow(follower, following));

        ensureFollowCountExists(follower);
        ensureFollowCountExists(following);

        followCountRepository.incrementFolloweesCount(followerId);
        followCountRepository.incrementFollowersCount(followingId);
    }

    @Transactional
    public void unfollow(Long followerId, Long followingId) {
        User follower = userRepository.findById(followerId)
                .orElseThrow(FollowException::userNotFound);
        User following = userRepository.findById(followingId)
                .orElseThrow(FollowException::userNotFound);

        Follow follow = followRepository.findByFollowerAndFollowing(follower, following)
                .orElseThrow(FollowException::notFollowing);

        follow.delete();

        followCountRepository.decrementFolloweesCount(followerId);
        followCountRepository.decrementFollowersCount(followingId);
    }

    @Transactional(readOnly = true)
    public Page<User> getFollowers(Long userId, Pageable pageable) {
        User user = userRepository.findById(userId)
                .orElseThrow(FollowException::userNotFound);

        return followRepository.findByFollowing(user, pageable)
                .map(Follow::getFollower);
    }

    @Transactional(readOnly = true)
    public Page<User> getFollowees(Long userId, Pageable pageable) {
        User user = userRepository.findById(userId)
                .orElseThrow(FollowException::userNotFound);

        return followRepository.findByFollower(user, pageable)
                .map(Follow::getFollowing);
    }

    public FollowCount getFollowCount(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(FollowException::userNotFound);

        return followCountRepository.findByUser(user)
                .orElseGet(() -> new FollowCount(user));
    }

    private void ensureFollowCountExists(User user) {
        if (followCountRepository.findByUser(user).isEmpty()) {
            followCountRepository.save(new FollowCount(user));
        }
    }
}
