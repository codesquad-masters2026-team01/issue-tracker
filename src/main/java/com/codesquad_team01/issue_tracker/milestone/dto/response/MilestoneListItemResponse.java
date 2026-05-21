package com.codesquad_team01.issue_tracker.milestone.dto.response;

public record MilestoneListItemResponse (
        Long id,
        String name,
        String completionDate,
        String description,
        Integer openIssueNum,
        Integer closedIssueNum
) {
}
