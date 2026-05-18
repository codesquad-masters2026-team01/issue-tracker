package com.codesquad_team01.issue_tracker.label;

import org.springframework.stereotype.Service;

@Service
public class LabelService {

    public LabelPageResponse getLabelPageResponse(){

        return new LabelPageResponse();
    }
}
