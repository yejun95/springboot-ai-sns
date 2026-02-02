package com.apiece.springboot_sns_sample.domain.quote;

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
public class QuoteService {

    private final PostRepository postRepository;
    private final PostService postService;

    @Transactional
    public Post createQuote(Long quoteId, String content, User user) {
        // 인용할 게시글 존재 확인
        Post quotedPost = postService.getPost(quoteId);

        // 인용 게시글 생성
        Post quote = Post.createQuote(content, user, quoteId);
        Post savedQuote = postRepository.save(quote);

        // 원본 게시글의 리포스트 카운트 증가
        postService.incrementRepostCount(quoteId);

        return savedQuote;
    }

    public Post getQuote(Long quoteId) {
        Post quote = postRepository.findById(quoteId)
                .orElseThrow(PostException.PostNotFoundException::new);

        if (quote.getQuoteId() == null) {
            throw new QuoteException.NotQuoteException();
        }

        return quote;
    }

    @Transactional
    public Post updateQuote(Long quoteId, String content, Long userId) {
        Post quote = postRepository.findById(quoteId)
                .orElseThrow(PostException.PostNotFoundException::new);

        if (quote.getQuoteId() == null) {
            throw new QuoteException.NotQuoteException();
        }

        if (!quote.getUser().getId().equals(userId)) {
            throw new PostException.UnauthorizedException();
        }

        quote.updateContent(content);
        return quote;
    }

    @Transactional
    public void deleteQuote(Long quoteId, Long userId) {
        Post quote = postRepository.findById(quoteId)
                .orElseThrow(PostException.PostNotFoundException::new);

        if (quote.getQuoteId() == null) {
            throw new QuoteException.NotQuoteException();
        }

        if (!quote.getUser().getId().equals(userId)) {
            throw new PostException.UnauthorizedException();
        }

        postRepository.delete(quote);
    }
}
