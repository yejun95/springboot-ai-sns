package com.apiece.springboot_sns_sample.domain.reply;

import com.apiece.springboot_sns_sample.domain.post.Post;
import com.apiece.springboot_sns_sample.domain.post.PostRepository;
import com.apiece.springboot_sns_sample.domain.post.PostService;
import com.apiece.springboot_sns_sample.domain.post.PostException;
import com.apiece.springboot_sns_sample.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReplyService {

    private final PostRepository postRepository;
    private final PostService postService;

    @Transactional
    public Post createReply(Long parentId, String content, User user) {
        // 부모 게시글 존재 확인
        Post parentPost = postService.getPost(parentId);

        // 답글 생성
        Post reply = Post.createReply(content, user, parentId);
        Post savedReply = postRepository.save(reply);

        // 부모 게시글의 답글 카운트 증가
        postService.incrementReplyCount(parentId);

        return savedReply;
    }

    public List<Post> getRepliesByPostId(Long postId) {
        return postRepository.findByParentIdOrderByCreatedAtAsc(postId);
    }

    @Transactional
    public Post updateReply(Long replyId, String content, Long userId) {
        Post reply = postRepository.findById(replyId)
                .orElseThrow(PostException.PostNotFoundException::new);

        if (reply.getParentId() == null) {
            throw new ReplyException.NotReplyException();
        }

        if (!reply.getUser().getId().equals(userId)) {
            throw new PostException.UnauthorizedException();
        }

        reply.updateContent(content);
        return reply;
    }

    @Transactional
    public void deleteReply(Long replyId, Long userId) {
        Post reply = postRepository.findById(replyId)
                .orElseThrow(PostException.PostNotFoundException::new);

        if (reply.getParentId() == null) {
            throw new ReplyException.NotReplyException();
        }

        if (!reply.getUser().getId().equals(userId)) {
            throw new PostException.UnauthorizedException();
        }

        postRepository.delete(reply);
    }
}
