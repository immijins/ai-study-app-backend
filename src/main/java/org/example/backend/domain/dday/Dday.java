package org.example.backend.domain.dday;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "dday")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Dday {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "day_date", nullable = false)
    private LocalDate dayDate;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() { this.createdAt = LocalDateTime.now(); }

    @Builder
    public Dday (Long userId, String title, LocalDate dayDate) {
        this.userId = userId;
        this.title = title;
        this.dayDate = dayDate;
    }

    // 수정(타이틀, 날짜)
    public void updateDday(String title, LocalDate dayDate) {
        this.title = title;
        this.dayDate = dayDate;
    }
}
