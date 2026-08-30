package org.example.backend.domain.task;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class TaskStatResponse {
    private LocalDate planDate;
    private Long totalCount;
    private Long completedCount;
}
