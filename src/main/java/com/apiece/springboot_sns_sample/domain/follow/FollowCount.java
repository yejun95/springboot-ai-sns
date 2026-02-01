package com.apiece.springboot_sns_sample.domain.follow;

import com.apiece.springboot_sns_sample.domain.common.BaseEntity;
import com.apiece.springboot_sns_sample.domain.user.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Entity
@Getter
@Table(name = "follow_counts")
@SQLDelete(sql = "UPDATE follow_counts SET deleted_at = NOW() WHERE id = ?")
@SQLRestriction("deleted_at IS NULL")
public class FollowCount extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @Column(nullable = false)
    private Long followersCount = 0L;

    @Column(nullable = false)
    private Long followeesCount = 0L;

    protected FollowCount() {}

    public FollowCount(User user) {
        this.user = user;
        this.followersCount = 0L;
        this.followeesCount = 0L;
    }
}
