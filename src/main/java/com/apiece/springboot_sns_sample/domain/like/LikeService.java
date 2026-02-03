package com.apiece.springboot_sns_sample.domain.like;

import com.apiece.springboot_sns_sample.domain.post.Post;
import com.apiece.springboot_sns_sample.domain.post.PostRepository;
import com.apiece.springboot_sns_sample.domain.post.PostException;
import com.apiece.springboot_sns_sample.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LikeService {

    private final LikeRepository likeRepository;
    private final PostRepository postRepository;

    @Transactional
    public Like likePost(User user, Long postId) {
        // 이미 좋아요를 눌렀는지 확인
        if (likeRepository.existsByUserIdAndPostId(user.getId(), postId)) {
            throw LikeException.alreadyLiked();
        }

        // 게시글 조회
        Post post = postRepository.findById(postId)
                .orElseThrow(PostException.PostNotFoundException::new);

        // 좋아요 생성
        Like like = new Like(user, post);
        Like savedLike = likeRepository.save(like);

        // 게시글의 좋아요 수 증가
        post.incrementLikeCount();

        return savedLike;
    }

    @Transactional
    public void unlikePost(Long userId, Long postId) {
        // 좋아요 조회
        Like like = likeRepository.findByUserIdAndPostId(userId, postId)
                .orElseThrow(LikeException::notFound);

        // 좋아요 삭제
        likeRepository.delete(like);

        // 게시글의 좋아요 수 감소
        Post post = like.getPost();
        post.decrementLikeCount();
    }

    public boolean isLiked(Long userId, Long postId) {
        return likeRepository.existsByUserIdAndPostId(userId, postId);
    }
}
