package com.codesquad_team01.issue_tracker.issue.domain;

import lombok.Getter;
import org.springframework.data.annotation.Id;

@Getter
public class IssueLabel {
    @Id
    private final Long id;
    private final Long labelId;

    // Spring Data JDBC 및 객체 생성을 위한 전체 생성자
    public IssueLabel(Long id, Long labelId) {
        this.id = id;
        this.labelId = labelId;
    }
}