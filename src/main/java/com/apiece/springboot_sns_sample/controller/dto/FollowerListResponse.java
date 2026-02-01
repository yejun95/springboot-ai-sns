package com.apiece.springboot_sns_sample.controller.dto;

import org.springframework.data.domain.Page;

import java.util.List;

public record FollowerListResponse(
        List<FollowResponse> content,
        int page,
        int size,
        long totalElements,
        int totalPages,
        boolean hasNext
) {
    public static FollowerListResponse from(Page<FollowResponse> page) {
        return new FollowerListResponse(
                page.getContent(),
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages(),
                page.hasNext()
        );
    }
}
