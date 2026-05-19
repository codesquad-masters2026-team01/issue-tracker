package com.codesquad_team01.issue_tracker.label;

import com.codesquad_team01.issue_tracker.label.domain.Label;
import com.codesquad_team01.issue_tracker.label.repository.LabelRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJdbcTest
public class LabelRepositoryTest {

    @Autowired
    private LabelRepository labelRepository;

    @Test
    @DisplayName("Label 테이블 전체 데이터를 불러온다.")
    public void findAllTest(){
        labelRepository.save(new Label(null, "라벨1", "설명1",
                "#000000", "#111111", null));
        labelRepository.save(new Label(null, "라벨2", "설명2",
                "#000000", "#111111", null));
        labelRepository.save(new Label(null, "라벨3", "설명3",
                "#000000", "#111111", null));

        List<Label> labels = labelRepository.findAll();

        assertThat(labels).hasSize(3);

        assertThat(labels)
                .extracting(Label::getId)
                .containsExactlyInAnyOrder(1L, 2L, 3L);

        assertThat(labels)
                .extracting(Label::getName)
                .containsExactlyInAnyOrder("라벨1", "라벨2", "라벨3");

        assertThat(labels)
                .extracting(Label::getDescription)
                .containsExactlyInAnyOrder("설명1", "설명2", "설명3");
    }
}
