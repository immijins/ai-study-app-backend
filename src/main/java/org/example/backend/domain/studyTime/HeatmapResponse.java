package org.example.backend.domain.studyTime;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class HeatmapResponse {
    // 100일 공부 시간 통계용 Response
    private LocalDate date;
    private Long totalSeconds; // 하루 공부시간 (초)
}
