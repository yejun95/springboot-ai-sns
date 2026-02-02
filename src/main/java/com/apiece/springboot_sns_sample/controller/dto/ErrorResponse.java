package com.apiece.springboot_sns_sample.controller.dto;

public record ErrorResponse(
        String error
) {
    public static ErrorResponse of(String error) {
        return new ErrorResponse(error);
    }
}
