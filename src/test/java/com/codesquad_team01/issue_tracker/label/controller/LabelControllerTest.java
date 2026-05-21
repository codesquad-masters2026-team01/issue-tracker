package com.codesquad_team01.issue_tracker.label.controller;

import com.codesquad_team01.issue_tracker.label.dto.request.LabelAddRequest;
import com.codesquad_team01.issue_tracker.label.dto.response.LabelAddResponse;
import com.codesquad_team01.issue_tracker.label.dto.response.LabelListResponse;
import com.codesquad_team01.issue_tracker.label.dto.response.LabelMetaData;
import com.codesquad_team01.issue_tracker.label.dto.response.LabelPageResponse;
import com.codesquad_team01.issue_tracker.label.service.LabelService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.internal.verification.VerificationModeFactory.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(LabelController.class)
public class LabelControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private LabelService labelService;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("GET /api/labels 요청이 온 뒤 정상적으로 레이블 목록과 마일스톤의 개수를 조회하면 200 OK와 통합 데이터를 반환한다.")
    public void getLabels_Success() throws Exception {
        LabelMetaData labelMetaData = new LabelMetaData(3, 1);
        LabelListResponse label1 = new LabelListResponse(1L, "라벨1", "라벨1의 설명", "#000000", "#FCFBFB");
        LabelListResponse label2 = new LabelListResponse(2L, "라벨2", "라벨2의 설명", "#000000", "#FCFBFB");

        List<LabelListResponse> labels = Arrays.asList(label1, label2);

        LabelPageResponse mockResponse = new LabelPageResponse(labelMetaData, labels);

        given(labelService.getLabels()).willReturn(mockResponse);

        // 프론트엔드에게 전달할 JSON 데이터 명세
        mockMvc.perform(
                get("/api/labels")
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.metadata.labelCount").exists())
                .andExpect(jsonPath("$.data.metadata.milestoneCount").exists())
                .andExpect(jsonPath("$.data.labels.length()").value(2))
                .andExpect(jsonPath("$.data.labels[0].id").exists())
                .andExpect(jsonPath("$.data.labels[0].name").exists())
                .andExpect(jsonPath("$.data.labels[0].description").exists())
                .andExpect(jsonPath("$.data.labels[0].textColor").exists())
                .andExpect(jsonPath("$.data.labels[0].backgroundColor").exists());
    }

    @Test
    @DisplayName("POST /api/labels 요청이 온 뒤 정상적으로 DB에 레이블을 추가하면 " +
            "추가한 레이블의 정보를 담은 LabelCreatedResponse를 반환받고, 201 Created와 해당 데이터를 반환한다.")
    public void addLabel_Success() throws Exception {
        LabelAddRequest requestDto = new LabelAddRequest(
                "documentation",
                "서비스에 대한 개선 사항 또는 추가 사항",
                "#000000",
                "#004DE3"
        );

        LabelAddResponse mockResponseDto = new LabelAddResponse(
                4L,
                "documentation",
                "서비스에 대한 개선 사항 또는 추가 사항",
                "#000000",
                "#004DE3");

        given(labelService.addLabel(any(LabelAddRequest.class))).willReturn(mockResponseDto);

        mockMvc.perform(
                post("/api/labels")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto))
        )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("레이블 추가 성공"))
                .andExpect(jsonPath("$.data.id").value(4L))
                .andExpect(jsonPath("$.data.name").value("documentation"))
                .andExpect(jsonPath("$.data.description").value("서비스에 대한 개선 사항 또는 추가 사항"))
                .andExpect(jsonPath("$.data.textColor").value("#000000"))
                .andExpect(jsonPath("$.data.backgroundColor").value("#004DE3"));

        ArgumentCaptor<LabelAddRequest> requestCaptor = ArgumentCaptor.forClass(LabelAddRequest.class);
        then(labelService).should(times(1)).addLabel(requestCaptor.capture());
        LabelAddRequest capturedRequest = requestCaptor.getValue();
        assertThat(capturedRequest.name()).isEqualTo("documentation");
        assertThat(capturedRequest.description()).isEqualTo("서비스에 대한 개선 사항 또는 추가 사항");
        assertThat(capturedRequest.textColor()).isEqualTo("#000000");
        assertThat(capturedRequest.backgroundColor()).isEqualTo("#004DE3");
    }
}
