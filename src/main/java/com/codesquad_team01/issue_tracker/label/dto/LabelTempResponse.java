package com.codesquad_team01.issue_tracker.label.dto;

import com.codesquad_team01.issue_tracker.issue.domain.Label;

// TODO: 현재 하나의 작업물과 충돌 가능성이 있기 때문에 LabelTempResponse를 따로 생성 -> 추후 변경 요망
public record LabelTempResponse(
        Long id,
        String name,
        String description,
        String textColor,
        String backgroundColor
) {

    public static LabelTempResponse from(Label label){
        return new LabelTempResponse(label.getId(), label.getName(), label.getDescription(),
                label.getTextColor(), label.getBackgroundColor());
    }
}
