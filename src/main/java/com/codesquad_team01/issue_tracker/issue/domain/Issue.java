package com.codesquad_team01.issue_tracker.issue.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.MappedCollection;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Set;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

    @MappedCollection(idColumn = "issue_id")
    private Set<Assignee> assignees;

    @MappedCollection(idColumn = "issue_id")
    private Set<IssueLabel> issueLabels;

    public Issue(Long id, String title, String contents, Long milestoneId, Long authorId,
                 boolean isOpened, LocalDateTime createdAt, LocalDateTime deletedAt,
                 Set<Assignee> assignees, Set<IssueLabel> issueLabels) {
        this.id = id;
        this.title = title;
        this.contents = contents;
        this.milestoneId = milestoneId;
        this.authorId = authorId;
        this.isOpened = isOpened;
        this.createdAt = createdAt;
        this.deletedAt = deletedAt;
        this.assignees = assignees != null ? assignees : Collections.emptySet();
        this.issueLabels = issueLabels != null ? issueLabels : Collections.emptySet();
    }

    public Issue(String title, String contents, Long milestoneId, Long authorId,
                 Set<Assignee> assignees, Set<IssueLabel> issueLabels) {
        this(null, title, contents, milestoneId, authorId, true,
                LocalDateTime.now(), null, assignees, issueLabels);
    }

    public void delete() {
        this.deletedAt = LocalDateTime.now();
    }

    public void changeStatus(boolean isOpened) {
        this.isOpened = isOpened;
    }

    public void changeTitle(String title) {
        this.title = title;
    }

    public void changeContents(String contents) {this.contents = contents;}
}