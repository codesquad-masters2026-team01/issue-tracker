package com.codesquad_team01.issue_tracker.label;

import com.codesquad_team01.issue_tracker.label.dto.LabelMetaData;
import com.codesquad_team01.issue_tracker.label.dto.LabelPageResponse;
import com.codesquad_team01.issue_tracker.label.dto.LabelTempResponse;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class LabelService {

    public LabelPageResponse getLabelPageResponse(){

        return new LabelPageResponse(
                new LabelMetaData(3, 2),
                new ArrayList<LabelTempResponse>()
        );
    }
}
