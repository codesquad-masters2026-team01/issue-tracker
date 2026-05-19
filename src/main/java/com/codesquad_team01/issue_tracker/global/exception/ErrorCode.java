package com.codesquad_team01.issue_tracker.global.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {
    INVALID_QUERY_MESSAGE("잘못된 이슈 아이디를 쿼리에 보냈습니다."),
    CAN_NOT_FOUND_THE_PAGE("요청하신 이슈 상세 페이지를 찾을 수 없습니다.");

    private final String message;

    ErrorCode(String message) {
        this.message = message;
    }
}