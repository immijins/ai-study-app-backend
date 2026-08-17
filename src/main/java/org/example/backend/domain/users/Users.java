package org.example.backend.domain.users;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 중복, null값 불가
    @Column(nullable = false, unique = true, length = 255)
    private String email;

    // 임시 비밀번호 보안 처리 X
    @Column(nullable = false, length = 255)
    private String password;

    // 닉네임은 중복 가능
    @Column(nullable = false, length = 255)
    private String nickname;

    // 경험치
    @Column(nullable = false)
    private Integer exp = 0;

    // 연속출석 일수
    @Column(name = "streak_days", nullable = false)
    private Integer streakDays = 0;

    // 마지막 공부 날짜
    @Column(name = "last_study_date")
    private LocalDate lastStudyDate;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    // 아이디 생성 일자에는 경험치, 연속날짜 0
    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
        if (this.exp == null) this.exp = 0;
        if (this.streakDays == null) this.streakDays = 0;
    }

    @Builder
    public Users(String email, String password, String nickname, Integer exp, Integer streakDays, LocalDate lastStudyDate) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.exp = (exp != null) ? exp : 0;
        this.streakDays = (streakDays != null) ? streakDays : 0;
        this.lastStudyDate = lastStudyDate;
    }


}
