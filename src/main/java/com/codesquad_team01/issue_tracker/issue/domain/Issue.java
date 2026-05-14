package com.codesquad_team01.issue_tracker.issue.domain;

import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import java.time.LocalDateTime;

@Getter
public class Issue {

    @Id
    private Long id;
    private String title;
    private String contents;
    private Long milestoneId;
    private Long authorId;
    private boolean isOpened;
    private LocalDateTime createdAt;
    private LocalDateTime deletedAt;


    public Issue(Long id, String title, String contents, Long milestoneId,
                 Long authorId, boolean isOpened,  LocalDateTime createdAt, LocalDateTime deletedAt) {
        this.id = id;
        this.title = title;
        this.contents = contents;
        this.milestoneId = milestoneId;
        this.authorId = authorId;
        this.isOpened = isOpened;
        this.createdAt = createdAt;
        this.deletedAt = deletedAt;
    }
}
