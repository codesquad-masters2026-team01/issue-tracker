package com.codesquad_team01.issue_tracker.label.controller;

import com.codesquad_team01.issue_tracker.global.dto.ApiResponse;
import com.codesquad_team01.issue_tracker.label.dto.response.LabelPageResponse;
import com.codesquad_team01.issue_tracker.label.service.LabelService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/labels")
public class LabelController {
    private final LabelService labelService;

    public LabelController(LabelService labelService) {
        this.labelService = labelService;
    }

    @GetMapping
    public ApiResponse<LabelPageResponse> getLabels() {
        LabelPageResponse responseData = labelService.getLabelPageResponse();
        return ApiResponse.success("레이블 페이지 로딩 성공", responseData);
    }
}