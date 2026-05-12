package com.codesquad_team01.issue_tracker.issue.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import java.time.LocalDateTime;

public class Issue {

    @Id
    private Long id;

    private String title;

    @Column("contents")
    private String content;

    @Column("is_opened")
    private boolean isOpened;

    private Long authorId;
    private Long milestoneId;


    private LocalDateTime createdAt;
    private LocalDateTime deletedAt;


    public Issue(String title, String content, boolean isOpened, Long authorId,
                 Long milestoneId,  LocalDateTime createdAt, LocalDateTime deletedAt) {
        this.title = title;
        this.content = content;
        this.isOpened = isOpened;
        this.authorId = authorId;
        this.milestoneId = milestoneId;
        this.createdAt = createdAt;
        this.deletedAt = deletedAt;
    }

    public Long getId() {return id;}
    public String getTitle() {return title;}
    public String getContent() {return content;}
    public boolean isOpened() {return isOpened;}
    public long getAuthorId() {return authorId;}
    public Long getMilestoneId() {return milestoneId;}
    public LocalDateTime getCreatedAt() {return createdAt;}
    public LocalDateTime getDeletedAt() {return deletedAt;}

}
