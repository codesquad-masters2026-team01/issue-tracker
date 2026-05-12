package com.codesquad_team01.issue_tracker.issue.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;

import java.time.LocalDateTime;

public class Label {

    @Id
    private Long id;

    private String name;
    private String description;

    @Column("text_color")
    private String textColor;

    @Column("background_color")
    private String backgroundColor;

    private LocalDateTime deletedAt;

    public Label(Long id ,String name, String description, String textColor, String backgroundColor
    , LocalDateTime deletedAt) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.textColor = textColor;
        this.backgroundColor = backgroundColor;
        this.deletedAt = deletedAt;
    }

    public Long getId() {return id;}
    public String getName() {return name;}
    public String getDescription() {return description;}
    public String getTextColor() {return textColor;}
    public String getBackgroundColor() {return backgroundColor;}
    public LocalDateTime getDeletedAt() {return deletedAt;}



}
