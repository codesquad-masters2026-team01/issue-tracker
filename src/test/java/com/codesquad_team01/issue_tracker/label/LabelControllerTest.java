package com.codesquad_team01.issue_tracker.label;

import com.codesquad_team01.issue_tracker.label.controller.LabelController;
import com.codesquad_team01.issue_tracker.label.dto.response.LabelListResponse;
import com.codesquad_team01.issue_tracker.label.dto.response.LabelMetaData;
import com.codesquad_team01.issue_tracker.label.dto.response.LabelPageResponse;
import com.codesquad_team01.issue_tracker.label.service.LabelService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(LabelController.class)
public class LabelControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private LabelService labelService;

    @Test
    @DisplayName("GET /api/labels 요청이 온 뒤 정상적으로 라벨 목록과 마일스톤의 개수를 조회하면 200 OK와 통합 데이터를 반환한다.")
    public void getLabels_Success() throws Exception {
        LabelMetaData labelMetaData = new LabelMetaData(3, 1);
        LabelListResponse label1 = new LabelListResponse(1L, "라벨1", "라벨1의 설명", "#000000", "#FCFBFB");
        LabelListResponse label2 = new LabelListResponse(2L, "라벨2", "라벨2의 설명", "#000000", "#FCFBFB");

        List<LabelListResponse> labels = Arrays.asList(label1, label2);

        LabelPageResponse mockResponse = new LabelPageResponse(labelMetaData, labels);

        given(labelService.getLabelPageResponse()).willReturn(mockResponse);

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
}
