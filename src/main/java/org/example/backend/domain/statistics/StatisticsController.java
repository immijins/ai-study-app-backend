package org.example.backend.domain.statistics;

import lombok.RequiredArgsConstructor;
import org.example.backend.common.annotation.CurrentUser;
import org.example.backend.domain.studyTime.HeatmapResponse;
import org.example.backend.domain.studyTime.StudyTimeRepository;
import org.example.backend.domain.task.TaskRepository;
import org.example.backend.domain.task.TaskStatResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/statistics")
public class StatisticsController {

    private final StudyTimeRepository studyTimeRepository;
    private final TaskRepository taskRepository;

    // 공부 시간 통계(잔디심기)
    @GetMapping("/heatmap")
    // GET /api/statistics/heatmap
    public List<HeatmapResponse> getHeatmap(
            @CurrentUser Long userId) {
        // 오늘 기준으로 100일 전 날짜 계산
        LocalDate startDate = LocalDate.now().minusDays(99);

        return studyTimeRepository.getHeatmapData(userId, startDate);
    }

    // 완료된 할일 계산
    @GetMapping("/tasklist")
    public List<TaskStatResponse> getTaskStats(@CurrentUser Long userId) {
        LocalDate startDate = LocalDate.now().minusDays(6); // 일주일 통계

        return taskRepository.getDailyTaskStats(userId, startDate);
    }
}
