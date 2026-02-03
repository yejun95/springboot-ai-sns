package com.apiece.springboot_sns_sample.config;

import com.apiece.springboot_sns_sample.controller.dto.ErrorResponse;
import com.apiece.springboot_sns_sample.domain.follow.FollowException;
import com.apiece.springboot_sns_sample.domain.like.LikeException;
import com.apiece.springboot_sns_sample.domain.post.PostException;
import com.apiece.springboot_sns_sample.domain.quote.QuoteException;
import com.apiece.springboot_sns_sample.domain.reply.ReplyException;
import com.apiece.springboot_sns_sample.domain.repost.RepostException;
import com.apiece.springboot_sns_sample.domain.user.UserException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserException.UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFoundException(UserException.UserNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ErrorResponse.of(e.getMessage()));
    }

    @ExceptionHandler(UserException.DuplicateUsernameException.class)
    public ResponseEntity<ErrorResponse> handleDuplicateUsernameException(UserException.DuplicateUsernameException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(ErrorResponse.of(e.getMessage()));
    }

    @ExceptionHandler(PostException.PostNotFoundException.class)
    public ResponseEntity<ErrorResponse> handlePostNotFoundException(PostException.PostNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ErrorResponse.of(e.getMessage()));
    }

    @ExceptionHandler(PostException.UnauthorizedException.class)
    public ResponseEntity<ErrorResponse> handleUnauthorizedException(PostException.UnauthorizedException e) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(ErrorResponse.of(e.getMessage()));
    }

    @ExceptionHandler(ReplyException.NotReplyException.class)
    public ResponseEntity<ErrorResponse> handleNotReplyException(ReplyException.NotReplyException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ErrorResponse.of(e.getMessage()));
    }

    @ExceptionHandler(QuoteException.NotQuoteException.class)
    public ResponseEntity<ErrorResponse> handleNotQuoteException(QuoteException.NotQuoteException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ErrorResponse.of(e.getMessage()));
    }

    @ExceptionHandler(RepostException.NotRepostException.class)
    public ResponseEntity<ErrorResponse> handleNotRepostException(RepostException.NotRepostException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ErrorResponse.of(e.getMessage()));
    }

    @ExceptionHandler(FollowException.class)
    public ResponseEntity<ErrorResponse> handleFollowException(FollowException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ErrorResponse.of(e.getMessage()));
    }

    @ExceptionHandler(LikeException.class)
    public ResponseEntity<ErrorResponse> handleLikeException(LikeException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ErrorResponse.of(e.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception e) {
        log.error("Unhandled exception occurred", e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ErrorResponse.of("서버 오류가 발생했습니다."));
    }
}
