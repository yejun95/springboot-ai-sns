package com.apiece.springboot_sns_sample.domain.repost;

import com.apiece.springboot_sns_sample.domain.post.Post;
import com.apiece.springboot_sns_sample.domain.post.PostRepository;
import com.apiece.springboot_sns_sample.domain.post.PostService;
import com.apiece.springboot_sns_sample.domain.post.PostException;
import com.apiece.springboot_sns_sample.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RepostService {

    private final PostRepository postRepository;
    private final PostService postService;

    @Transactional
    public Post createRepost(Long repostId, User user) {
        // 리포스트할 게시글 존재 확인
        Post originalPost = postService.getPost(repostId);

        // 리포스트 생성
        Post repost = Post.createRepost(user, repostId);
        Post savedRepost = postRepository.save(repost);

        // 원본 게시글의 리포스트 카운트 증가
        postService.incrementRepostCount(repostId);

        return savedRepost;
    }

    public Post getRepost(Long repostId) {
        Post repost = postRepository.findById(repostId)
                .orElseThrow(PostException.PostNotFoundException::new);

        if (repost.getRepostId() == null) {
            throw new RepostException.NotRepostException();
        }

        return repost;
    }

    @Transactional
    public void deleteRepost(Long repostId, Long userId) {
        Post repost = postRepository.findById(repostId)
                .orElseThrow(PostException.PostNotFoundException::new);

        if (repost.getRepostId() == null) {
            throw new RepostException.NotRepostException();
        }

        if (!repost.getUser().getId().equals(userId)) {
            throw new PostException.UnauthorizedException();
        }

        postRepository.delete(repost);
    }
}
