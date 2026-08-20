package org.example.backend.domain.task;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class TaskScheduleRequest {
    private LocalDate planDate;
    private Long categoryId;
}
