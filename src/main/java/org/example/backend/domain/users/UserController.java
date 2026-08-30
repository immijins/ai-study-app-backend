package org.example.backend.domain.users;

import org.example.backend.common.annotation.CurrentUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // 프로필 정보 가져오기
    @GetMapping("/info")
    public UserProfileResponse getMyProfile(@CurrentUser Long userId) {
        Users user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("유저를 찾을 수 없습니다."));

        return new UserProfileResponse(user.getNickname(), user.getLevel(), user.getStreakDays(), user.getExp());
    }

}
