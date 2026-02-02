package com.apiece.springboot_sns_sample.domain.post;

import com.apiece.springboot_sns_sample.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    public Post createPost(String content, User user) {
        Post post = Post.createPost(content, user);
        return postRepository.save(post);
    }

    public Post getPost(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(PostException.PostNotFoundException::new);
    }

    @Transactional
    public Post getPostAndIncrementView(Long postId) {
        Post post = getPost(postId);
        postRepository.incrementViewCount(postId);
        return post;
    }

    public Page<Post> getPostsByUser(Long userId, Pageable pageable) {
        return postRepository.findByUserIdOrderByCreatedAtDesc(userId, pageable);
    }

    public Page<Post> getTimelinePosts(Pageable pageable) {
        return postRepository.findByParentIdIsNullOrderByCreatedAtDesc(pageable);
    }

    @Transactional
    public Post updatePost(Long postId, String content, Long userId) {
        Post post = getPost(postId);

        if (!post.getUser().getId().equals(userId)) {
            throw new PostException.UnauthorizedException();
        }

        post.updateContent(content);
        return post;
    }

    @Transactional
    public void deletePost(Long postId, Long userId) {
        Post post = getPost(postId);

        if (!post.getUser().getId().equals(userId)) {
            throw new PostException.UnauthorizedException();
        }

        postRepository.delete(post);
    }

    @Transactional
    public void incrementReplyCount(Long postId) {
        postRepository.incrementReplyCount(postId);
    }

    @Transactional
    public void incrementRepostCount(Long postId) {
        postRepository.incrementRepostCount(postId);
    }
}
