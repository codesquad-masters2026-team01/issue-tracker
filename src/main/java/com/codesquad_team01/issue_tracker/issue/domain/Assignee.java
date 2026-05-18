package com.codesquad_team01.issue_tracker.issue.domain;

import lombok.Getter;
import org.springframework.data.annotation.Id;

@Getter
public class Assignee {
    @Id
    private final Long id;
    private final Long memberId;

    // Spring Data JDBC 및 객체 생성을 위한 전체 생성자
    public Assignee(Long id, Long memberId) {
        this.id = id;
        this.memberId = memberId;
    }
}