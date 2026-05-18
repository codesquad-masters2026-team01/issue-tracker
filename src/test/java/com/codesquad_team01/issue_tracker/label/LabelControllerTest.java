package com.codesquad_team01.issue_tracker.label;

import com.codesquad_team01.issue_tracker.label.dto.LabelMetaData;
import com.codesquad_team01.issue_tracker.label.dto.LabelPageResponse;
import com.codesquad_team01.issue_tracker.label.dto.LabelTempResponse;
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

    /* TODO
        1. 요청(Request) 검증: 올바른 URL, HTTP Method(GET), Header(Authorization)를 잘 매핑하여 받아들이는가?
        2. 응답(Response) 검증: 우리가 설계한 DTO 구조가 올바른 JSON 포맷으로 직렬화(Serialization)되어 반환되는가?
        3. 상태 코드(Status Code): 상황에 맞는 HTTP 상태 코드(200, 401 등)를 반환하는가?
     */

    @Test
    @DisplayName("GET /api/labels 요청이 온 뒤 정상적으로 라벨 목록과 마일스톤의 개수를 조회하면 200 OK와 통합 데이터를 반환한다.")
    public void getLabels_Success() throws Exception {
        LabelMetaData labelMetaData = new LabelMetaData(3, 1);
        LabelTempResponse label1
                = new LabelTempResponse(1L, "라벨1", "라벨1의 설명", "#000000", "#FCFBFB");
        LabelTempResponse label2
                = new LabelTempResponse(2L, "라벨2", "라벨2의 설명", "#000000", "#FCFBFB");
        List<LabelTempResponse> labels = Arrays.asList(label1, label2);

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
