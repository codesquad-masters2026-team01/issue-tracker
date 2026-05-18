package com.codesquad_team01.issue_tracker.label;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LabelController {

    private final LabelService labelService;

    public LabelController(LabelService labelService) {
        this.labelService = labelService;
    }

    @GetMapping("/api/labels")
    public LabelPageResponse getLabelList(){

        return new LabelPageResponse();
    }
}
