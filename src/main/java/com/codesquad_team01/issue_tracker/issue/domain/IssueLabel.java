package com.codesquad_team01.issue_tracker.issue.domain;

import org.springframework.data.annotation.Id;

public record IssueLabel(
        @Id Long id,
        Long issueId,
        Long labelId
) {}
