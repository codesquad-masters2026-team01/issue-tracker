package com.codesquad_team01.issue_tracker.issue.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import java.time.LocalDateTime;

public class Issue {

    @Id
    private Long id;

    private String title;
    private String content;

    @Column("is_opened")
    private boolean isOpened;

    private final Long authorId;
    private final Long milestoneId;


    private final LocalDateTime createdAt;
    private final LocalDateTime deletedAt;


    public Issue(String title, String content, boolean isOpened, Long authorId, Long milestoneId) {
        this.title = title;
        this.content = content;
        this.isOpened = isOpened;
        this.authorId = authorId;
        this.milestoneId = milestoneId;
        this.createdAt = LocalDateTime.now();
        this.deletedAt = null;
    }
}
