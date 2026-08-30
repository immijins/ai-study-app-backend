package org.example.backend.domain.studyTime;

import lombok.RequiredArgsConstructor;
import org.example.backend.domain.users.UserRepository;
import org.example.backend.domain.users.Users;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class StudyTimeService {

    private final StudyTimeRepository studyTimeRepository;
    private final UserRepository userRepository;

    // 공부 시간 저장 (타이머 종료 시)
    @Transactional
    public StudyTimeResponse saveStudyTime(Long userId, StudyTimeRequest request) {
        StudyTime studyTime = new StudyTime(
                userId,
                request.getDurationSeconds(),
                request.getStartedAt(),
                request.getEndedAt(),
                request.getStudyDate()
        );

        StudyTime saved = studyTimeRepository.save(studyTime);

        // 일일 공부 시간 달성 시 적용
        Users users = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("유저를 찾을 수 없습니다."));

        LocalDate today = request.getStudyDate();
        Integer todayTotalSeconds = studyTimeRepository.getTotalStudySecondsByDate(userId, today);

        // 누적 시간이 1시간 이상이면
        if (todayTotalSeconds >= 3600) {
            // 연속 출석 인정
            users.recordDailyStudy(today);

            // 경험치 추가
            users.addExp(50);
        }

        return new StudyTimeResponse(saved.getId(), saved.getDurationSeconds());
    }

    // 특정 날짜의 총 누적 공부 시간 조회
    @Transactional(readOnly = true)
    public DailyStudyTotalResponse getTodayTotalStudyTime(Long userId, LocalDate date) {
        Integer totalSeconds = studyTimeRepository.getTotalStudySecondsByDate(userId, date);
        return new DailyStudyTotalResponse(totalSeconds);
    }

}
