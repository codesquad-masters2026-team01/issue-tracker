package com.codesquad_team01.issue_tracker.issue.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;


public class Milestone {

    @Id
    private Long id;

    private String name;

    @Column("completion_date")
    private LocalDate completionDate;

    private String description;

    @Column("is_opened")
    private Boolean isOpened;

    private LocalDateTime deletedAt;

    public Milestone(Long id, String name, LocalDate completionDate,
                     String description, Boolean isOpened, LocalDateTime deletedAt) {
        this.id = id;
        this.name = name;
        this.completionDate = completionDate;
        this.description = description;
        this.isOpened = isOpened;
        this.deletedAt = deletedAt;
    }

    public Long getId() {return id;}
    public String getName() {return name;}
    public LocalDate getCompletionDate() {return completionDate;}
    public String getDescription() {return description;}
    public Boolean getIsOpened() {return isOpened;}
    public LocalDateTime getDeletedAt() {return deletedAt;}

}
