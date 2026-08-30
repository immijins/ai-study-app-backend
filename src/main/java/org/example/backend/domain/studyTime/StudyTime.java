package org.example.backend.domain.studyTime;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "studytime")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StudyTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "duration_seconds", nullable = false)
    private Integer durationSeconds;

    @Column(name = "started_at", nullable = false)
    private LocalDateTime startedAt;

    @Column(name = "ended_at", nullable = false)
    private LocalDateTime endedAt;

    @Column(name = "study_date", nullable = false)
    private LocalDate studyDate;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() { this.createdAt = LocalDateTime.now(); }

    @Builder
    public StudyTime (Long userId, Integer durationSeconds, LocalDateTime startedAt, LocalDateTime endedAt, LocalDate studyDate) {
        this.userId = userId;
        this.durationSeconds = durationSeconds;
        this.startedAt = startedAt;
        this.endedAt = endedAt;
        this.studyDate = studyDate;
    }
}
