package com.codesquad_team01.issue_tracker.issue.domain;

import lombok.Getter;
import org.springframework.data.annotation.Id;
import java.time.LocalDateTime;

@Getter
public class Issue {
    @Id
    private final Long id;
    private final String title;
    private final String contents;
    private final Long milestoneId;
    private final Long authorId;
    private final boolean isOpened;
    private final LocalDateTime createdAt;
    private final LocalDateTime deletedAt;


    public Issue(Long id, String title, String contents, Long milestoneId, Long authorId,
                 boolean isOpened, LocalDateTime createdAt, LocalDateTime deletedAt ) {

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
