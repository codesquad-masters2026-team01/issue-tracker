package com.codesquad_team01.issue_tracker.label.dto;

public record LabelMetaData (
        // SELECT COUNT(*) FROM label 같은 쿼리를 날린다?
        // MySQL은 64비트 정수형(BIGINT)으로 반환
        // 굳이 다운캐스팅 할 필요가 없다!
        // Long이 아닌 long으로 하는게 권장됨 -> 라벨이 없다는 것은 null이 아닌 0으로 표현되어야 하는 명확한 도메인이기 때문
        long labelCount,
        long milestoneCount
){}
