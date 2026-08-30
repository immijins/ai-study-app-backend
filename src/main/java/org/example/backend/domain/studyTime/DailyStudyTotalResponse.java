package org.example.backend.domain.studyTime;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
// 하루 총 누적 시간 조회용
public class DailyStudyTotalResponse {
    private Integer totalSeconds;
}
