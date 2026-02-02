package com.apiece.springboot_sns_sample.domain.post;

import com.apiece.springboot_sns_sample.domain.common.BaseEntity;
import com.apiece.springboot_sns_sample.domain.user.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Entity
@Getter
@Table(name = "posts")
@SQLDelete(sql = "UPDATE posts SET deleted_at = NOW() WHERE id = ?")
@SQLRestriction("deleted_at IS NULL")
public class Post extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 1000)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "parent_id")
    private Long parentId; // 답글

    @Column(name = "quote_id")
    private Long quoteId; // 인용

    @Column(name = "repost_id")
    private Long repostId; // 리포스트

    @Column(nullable = false)
    private Integer repostCount = 0;

    @Column(nullable = false)
    private Integer likeCount = 0;

    @Column(nullable = false)
    private Integer replyCount = 0;

    @Column(nullable = false)
    private Long viewCount = 0L;

    protected Post() {}

    private Post(String content, User user) {
        this.content = content;
        this.user = user;
    }

    private Post(String content, User user, Long parentId) {
        this.content = content;
        this.user = user;
        this.parentId = parentId;
    }

    private Post(String content, User user, Long quoteId, Long repostId) {
        this.content = content;
        this.user = user;
        this.quoteId = quoteId;
        this.repostId = repostId;
    }

    // Factory methods for clear intent
    public static Post createPost(String content, User user) {
        return new Post(content, user);
    }

    public static Post createReply(String content, User user, Long parentId) {
        return new Post(content, user, parentId);
    }

    public static Post createQuote(String content, User user, Long quoteId) {
        return new Post(content, user, quoteId, null);
    }

    public static Post createRepost(User user, Long repostId) {
        return new Post(null, user, null, repostId);
    }

    public void incrementRepostCount() {
        this.repostCount++;
    }

    public void incrementLikeCount() {
        this.likeCount++;
    }

    public void decrementLikeCount() {
        if (this.likeCount > 0) {
            this.likeCount--;
        }
    }

    public void incrementReplyCount() {
        this.replyCount++;
    }

    public void incrementViewCount() {
        this.viewCount++;
    }

    public void updateContent(String content) {
        this.content = content;
    }
}
