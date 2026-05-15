package com.codesquad_team01.issue_tracker.issue.domain;

import org.springframework.data.annotation.Id;
import java.time.LocalDateTime;

public record Issue(
        @Id Long id,
        String title,
        String contents,
        Long milestoneId,
        Long authorId,
        boolean isOpened,
        LocalDateTime createdAt,
        LocalDateTime deletedAt
) {}
