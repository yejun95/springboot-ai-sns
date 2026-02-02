package com.apiece.springboot_sns_sample.domain.quote;

public class QuoteException extends RuntimeException {

    public QuoteException(String message) {
        super(message);
    }

    public static class NotQuoteException extends QuoteException {
        public NotQuoteException() {
            super("인용 게시글이 아닙니다.");
        }
    }
}
