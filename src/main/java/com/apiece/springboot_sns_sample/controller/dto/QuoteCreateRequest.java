package com.apiece.springboot_sns_sample.controller.dto;

public record QuoteCreateRequest(
        Long quoteId,
        String content
) {
}
