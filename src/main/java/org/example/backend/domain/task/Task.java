package org.example.backend.domain.task;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "tasks")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "plan_date", nullable = false)
    private LocalDate planDate;

    @Column(name = "category_id")
    private Long categoryId;

    @Column(name = "is_complete", nullable = false)
    private Boolean isComplete = false;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }

    @Builder
    public Task(Long userId, String title, LocalDate planDate, Long categoryId) {
        this.userId = userId;
        this.title = title;
        this.planDate = planDate;
        this.categoryId = categoryId;
        this.isComplete = false;
    }

    // 수정
    public void updateSchedule(LocalDate planDate, Long categoryId) {
        this.categoryId = categoryId;
        this.planDate = planDate;
    }

    // 완료
    public void toggleComplete() {
        this.isComplete = !this.isComplete;
    }
}
