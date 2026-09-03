package org.example.backend.domain.users;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class UserProfileResponse {
    private String nickname;
    private Integer level;
    private Integer streakDays;
    private Integer exp;
    private LocalDate lastStudyDate;
}
