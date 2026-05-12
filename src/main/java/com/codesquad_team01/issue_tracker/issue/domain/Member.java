package com.codesquad_team01.issue_tracker.issue.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;

import java.time.LocalDateTime;

public class Member {

    @Id
    private Long id;

    @Column("user_id")
    private String userId;

    private String name;
    private String password;
    private String email;
    private LocalDateTime deletedAt;

    public Member(Long id, String userId, String name, String password, String email, LocalDateTime deleteAt) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.password = password;
        this.email = email;
        this.deletedAt = deleteAt;
    }

    public Long getId() {return id;}
    public String getUserId() {return userId;}
    public String getName() {return name;}
    public String getPassword() {return password;}
    public String getEmail() {return email;}
    public LocalDateTime getDeletedAt() {return deletedAt;}
}
