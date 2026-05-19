package com.codesquad_team01.issue_tracker.global.exception;

import com.codesquad_team01.issue_tracker.global.dto.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IssueTrackerException.class)
    public ResponseEntity<ApiResponse<Void>> handleIssueTrackerException(IssueTrackerException e) {
        ErrorCode errorCode = e.getErrorCode();
        HttpStatus status = switch (errorCode) {
            case INVALID_QUERY_MESSAGE -> HttpStatus.BAD_REQUEST;
            case CAN_NOT_FOUND_THE_PAGE -> HttpStatus.NOT_FOUND;
        };

        return ResponseEntity.status(status)
                .body(ApiResponse.fail(errorCode.getMessage(), errorCode.name()));
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ApiResponse<Void>> handleTypeMismatchException(MethodArgumentTypeMismatchException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.fail(ErrorCode.INVALID_QUERY_MESSAGE.getMessage(), ErrorCode.INVALID_QUERY_MESSAGE.name()));
    }
}