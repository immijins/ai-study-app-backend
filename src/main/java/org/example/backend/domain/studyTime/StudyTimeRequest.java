package org.example.backend.domain.studyTime;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class StudyTimeRequest {
    private Integer durationSeconds;
    private LocalDateTime startedAt;
    private LocalDateTime endedAt;
    private LocalDate studyDate;
}
