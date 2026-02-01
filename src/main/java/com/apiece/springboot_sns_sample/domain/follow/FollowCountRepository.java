package com.apiece.springboot_sns_sample.domain.follow;

import com.apiece.springboot_sns_sample.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface FollowCountRepository extends JpaRepository<FollowCount, Long> {

    Optional<FollowCount> findByUser(User user);

    @Modifying
    @Query("UPDATE FollowCount fc SET fc.followersCount = fc.followersCount + 1 WHERE fc.user.id = :userId")
    void incrementFollowersCount(@Param("userId") Long userId);

    @Modifying
    @Query("UPDATE FollowCount fc SET fc.followersCount = fc.followersCount - 1 WHERE fc.user.id = :userId AND fc.followersCount > 0")
    void decrementFollowersCount(@Param("userId") Long userId);

    @Modifying
    @Query("UPDATE FollowCount fc SET fc.followeesCount = fc.followeesCount + 1 WHERE fc.user.id = :userId")
    void incrementFolloweesCount(@Param("userId") Long userId);

    @Modifying
    @Query("UPDATE FollowCount fc SET fc.followeesCount = fc.followeesCount - 1 WHERE fc.user.id = :userId AND fc.followeesCount > 0")
    void decrementFolloweesCount(@Param("userId") Long userId);
}
