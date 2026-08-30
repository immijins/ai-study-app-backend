package org.example.backend.domain.studyTime;

import lombok.RequiredArgsConstructor;
import org.example.backend.common.annotation.CurrentUser;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/studytime")
@RequiredArgsConstructor
public class StudyTimeController {

    private final StudyTimeService studyTimeService;

    // 공부시간 기록 저장
    // POST /api/studytime
    @PostMapping
    public ResponseEntity<StudyTimeResponse> saveStudyTime(
            @CurrentUser Long userId,
            @RequestBody StudyTimeRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(studyTimeService.saveStudyTime(userId, request));
    }

    // 특정 날짜의 누적 공부 시간 조회
    // GET /api/studytime/today?date=2026-08-23
    @GetMapping("/today")
    public ResponseEntity<DailyStudyTotalResponse> getTodayTotalTime(
            @CurrentUser Long userId,
            @RequestParam("date") @DateTimeFormat(pattern = "yyyy-MM-dd")LocalDate date) {
        return ResponseEntity.ok(studyTimeService.getTodayTotalStudyTime(userId, date));
    }
}
