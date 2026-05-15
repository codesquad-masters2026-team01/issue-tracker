package com.codesquad_team01.issue_tracker.issue.controller;

import com.codesquad_team01.issue_tracker.issue.dto.request.IssueWriteRequest;
import com.codesquad_team01.issue_tracker.global.dto.ApiResponse;
import com.codesquad_team01.issue_tracker.issue.dto.response.IssueListResponse;
import com.codesquad_team01.issue_tracker.issue.service.IssueService;
import com.codesquad_team01.issue_tracker.issue.service.IssueWriteService;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
public class IssueController {

    private final IssueService issueService;
    private final IssueWriteService issueWriteService;

    public IssueController(IssueService issueService, IssueWriteService issueWriteService) {
        this.issueService = issueService;
        this.issueWriteService = issueWriteService;

    }

    @GetMapping("/api/issues")
    public ApiResponse<IssueListResponse> getIssueList(@RequestParam(defaultValue = "open") String status) {
        boolean isOpened = status.equals("open");
        String message = isOpened ? "열린 이슈 페이지 로딩 성공" : "닫힌 이슈 페이지 로딩 성공";

        IssueListResponse data = issueService.getIssueList(isOpened);

        return ApiResponse.success(message, data);
    }



    @PostMapping(value = "/api/issues", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiResponse<Map<String, Long>> uploadIssue(
            @RequestPart("request") @Valid IssueWriteRequest issueWriteRequest,
            @RequestPart(value = "files", required = false) List<MultipartFile> files) {

        Long issueId = issueWriteService.writeIssue(issueWriteRequest,files);

        return ApiResponse.success("이슈 작성 완료",Map.of("issueId", issueId));
    }
}