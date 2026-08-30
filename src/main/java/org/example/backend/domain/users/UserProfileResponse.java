package org.example.backend.domain.users;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserProfileResponse {
    private String nickname;
    private Integer level;
    private Integer streakDays;
    private Integer exp;
}
