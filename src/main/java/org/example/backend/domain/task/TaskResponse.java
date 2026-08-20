package org.example.backend.domain.task;

import lombok.Getter;

import java.time.LocalDate;

@Getter
public class TaskResponse {
    private final Long id;
    private final String title;
    private final LocalDate planDate;
    private final Long categoryId;
    private final Boolean isComplete;

    public TaskResponse(Task task) {
        this.id = task.getId();
        this.title = task.getTitle();
        this.planDate = task.getPlanDate();
        this.categoryId = task.getCategoryId();
        this.isComplete = task.getIsComplete();
    }
}
