package com.codesquad_team01.issue_tracker.label.controller;

import com.codesquad_team01.issue_tracker.global.dto.ApiResponse;
import com.codesquad_team01.issue_tracker.label.domain.Label;
import com.codesquad_team01.issue_tracker.label.service.LabelService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/api/labels")
public class LabelController {
    private final LabelService labelService;

    public LabelController(LabelService labelService) {
        this.labelService = labelService;
    }

    @GetMapping
    public ApiResponse<List<Label>> getLabels() {
        return ApiResponse.success(labelService.findAll());
    }
}