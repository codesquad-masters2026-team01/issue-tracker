package com.codesquad_team01.issue_tracker.global.dto;

public record ApiResponse<T>(
        boolean success,
        String message,
        T data
) {
    public static <T> ApiResponse<T> success(String message, T data) {
        return new ApiResponse<T>(true, message,data);
    }
}
