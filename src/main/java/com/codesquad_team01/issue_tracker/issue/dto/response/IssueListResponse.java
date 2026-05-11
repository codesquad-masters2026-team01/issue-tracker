package com.codesquad_team01.issue_tracker.issue.dto.response;

import java.util.List;

public class IssueListResponse {

    private final List<IssueResponse> issues;
    private final int openCount;
    private final int closeCount;
    private final int labelsCount;
    private final int milestoneCount;


    public IssueListResponse(List<IssueResponse> issues, int openCount, int closeCount
    , int labelsCount, int milestoneCount) {
        this.issues = issues;
        this.openCount = openCount;
        this.closeCount = closeCount;
        this.labelsCount = labelsCount;
        this.milestoneCount = milestoneCount;
    }

    public List<IssueResponse> getIssues() {return issues;}
    public int getOpenCount() {return openCount;}
    public int getCloseCount() {return closeCount;}
    public int getLabelsCount() {return labelsCount;}
    public int getMilestoneCount() {return milestoneCount;}
}
