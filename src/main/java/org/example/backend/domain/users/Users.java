package org.example.backend.domain.users;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

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

    // 레벨 필드(기본값 0)
    @Column(nullable = false)
    private Integer level = 0;

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
        if (this.level == null) this.level = 0;
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

    // 게임 로직 메서드 추가

    // 경험치 획득 및 자동 레벨업
    public void addExp(int amount) {
        this.exp += amount;
        updateLevel();
    }

    // 경험치에 따른 레벨 구간
    private void updateLevel() {
        if (this.exp >= 1001) { // 1,001 ~
            this.level = 4;
        } else if (this.exp >= 501) { // 501 ~ 1,000
            this.level = 3;
        } else if (this.exp >= 201) { // 201 ~ 500
            this.level = 2;
        } else if (this.exp >= 51) { // 51 ~ 200
            this.level = 1;
        } else { // 0 ~ 50
            this.level = 0;
        }
    }

    // 일일 학습 달성 시 연속 학습일 업데이트 (1시간 이상)
    public void recordDailyStudy(LocalDate today) {
        if (this.lastStudyDate == null) {
            this.streakDays = 1;
        } else {
            long daysBetween = ChronoUnit.DAYS.between(this.lastStudyDate, today);
            if (daysBetween == 1) {
                this.streakDays += 1;
            } else if (daysBetween > 1) {
                this.streakDays = 1;
            }
        }

        this.lastStudyDate = today;
    }

    // 결석 상태 확인 로직
    public int getMissedDays(LocalDate today) {
        if (this.lastStudyDate == null) return 0;
        long daysBetween = ChronoUnit.DAYS.between(this.lastStudyDate, today);
        return daysBetween > 1 ? (int)(daysBetween - 1) : 0;
    }

}
