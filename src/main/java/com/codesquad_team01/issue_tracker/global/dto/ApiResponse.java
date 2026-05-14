package com.codesquad_team01.issue_tracker.global.dto;

public record ApiResponse<T>(
        boolean success,
        String message,
        T data
) {
    // 1. 데이터만 인자로 받는 경우 (컨트롤러에서 사용 중인 방식)
    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(true, "성공", data);
    }

    // 2. 메시지와 데이터를 모두 인자로 받는 경우
    public static <T> ApiResponse<T> success(String message, T data) {
        return new ApiResponse<>(true, message, data);
    }
}