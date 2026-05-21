package com.codesquad_team01.issue_tracker.label.dto.request;

import java.util.List;

public record LabelUpdateRequest (
        List<Long> labelIds
){
}
