package com.codesquad_team01.issue_tracker.label.dto.response;

public record LabelAddResponse (
        Long id,
        String name,
        String description,
        String textColor,
        String backgroundColor
){}
