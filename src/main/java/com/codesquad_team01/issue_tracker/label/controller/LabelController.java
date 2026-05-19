package com.codesquad_team01.issue_tracker.label.controller;

import com.codesquad_team01.issue_tracker.global.dto.ApiResponse;
import com.codesquad_team01.issue_tracker.label.dto.request.LabelAddRequest;
import com.codesquad_team01.issue_tracker.label.dto.response.LabelAddResponse;
import com.codesquad_team01.issue_tracker.label.dto.response.LabelPageResponse;
import com.codesquad_team01.issue_tracker.label.service.LabelService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/labels")
public class LabelController {
    private final LabelService labelService;

    public LabelController(LabelService labelService) {
        this.labelService = labelService;
    }

    // TODO: 레이블 정렬(가장 최근 생긴 레이블이 위로 간다), 삭제된 인스턴스 고려
    @GetMapping
    public ApiResponse<LabelPageResponse> getLabels() {
        LabelPageResponse responseData = labelService.getLabelPageResponse();
        return ApiResponse.success("레이블 페이지 로딩 성공", responseData);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<LabelAddResponse> addLabel(@Valid @RequestBody LabelAddRequest labelAddRequest) {
        LabelAddResponse responseData = labelService.addLabel(labelAddRequest);
        return ApiResponse.success("레이블 추가 성공", responseData);
    }
}