package org.example.backend.domain.studyTime;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class StudyTimeResponse {
    private Long id;
    private Integer durationSeconds;
}
