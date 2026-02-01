-- users 테이블
CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    email VARCHAR(255) UNIQUE,
    username VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    nickname VARCHAR(255),
    bio TEXT,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    deleted_at TIMESTAMP
);

CREATE INDEX idx_users_deleted_at ON users(deleted_at);
CREATE INDEX idx_users_email_deleted_at ON users(email, deleted_at);
CREATE INDEX idx_users_username_deleted_at ON users(username, deleted_at);


-- follows 테이블
CREATE TABLE follows (
    id BIGSERIAL PRIMARY KEY,
    follower_id BIGINT NOT NULL,
    following_id BIGINT NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    deleted_at TIMESTAMP,
    CONSTRAINT fk_follows_follower FOREIGN KEY (follower_id) REFERENCES users(id),
    CONSTRAINT fk_follows_following FOREIGN KEY (following_id) REFERENCES users(id),
    CONSTRAINT uk_follower_following UNIQUE (follower_id, following_id)
);

CREATE INDEX idx_follows_following_id ON follows(following_id);
CREATE INDEX idx_follows_follower_created ON follows(follower_id, created_at DESC);
CREATE INDEX idx_follows_following_created ON follows(following_id, created_at DESC);
CREATE INDEX idx_follows_follower_deleted ON follows(follower_id, deleted_at);
CREATE INDEX idx_follows_following_deleted ON follows(following_id, deleted_at);


-- follow_counts 테이블
CREATE TABLE follow_counts (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL UNIQUE,
    followers_count BIGINT NOT NULL DEFAULT 0,
    followees_count BIGINT NOT NULL DEFAULT 0,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    deleted_at TIMESTAMP,
    CONSTRAINT fk_follow_counts_user FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE INDEX idx_follow_counts_followers ON follow_counts(followers_count DESC);
CREATE INDEX idx_follow_counts_followees ON follow_counts(followees_count DESC);
