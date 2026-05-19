package com.codesquad_team01.issue_tracker.label;

import com.codesquad_team01.issue_tracker.global.dto.ApiResponse;
import com.codesquad_team01.issue_tracker.label.dto.LabelPageResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LabelController {

    private final LabelService labelService;

    public LabelController(LabelService labelService) {
        this.labelService = labelService;
    }

    @GetMapping("/api/labels")
    public ApiResponse<LabelPageResponse> getLabelList(){
        LabelPageResponse responseData = labelService.getLabelPageResponse();

        return ApiResponse.ok("레이블 페이지 로딩 성공", responseData);
    }
}
