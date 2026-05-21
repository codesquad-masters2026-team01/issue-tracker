package com.codesquad_team01.issue_tracker.label.dto.response;

import com.codesquad_team01.issue_tracker.label.domain.Label;

public record LabelAddResponse (
        Long id,
        String name,
        String description,
        String textColor,
        String backgroundColor
){

    public static LabelAddResponse labelToResponse(Label label){
        return new LabelAddResponse(label.getId(), label.getName(), label.getDescription(),
                label.getTextColor(), label.getBackgroundColor());
    }
}