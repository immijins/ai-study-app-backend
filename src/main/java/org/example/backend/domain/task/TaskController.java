package org.example.backend.domain.task;

import lombok.RequiredArgsConstructor;
import org.example.backend.common.annotation.CurrentUser;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("/api/task")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    // 할 일 등록
    // POST /api/task
    @PostMapping
    public ResponseEntity<TaskResponse> createTask(
            @CurrentUser Long userId,
            @RequestBody TaskRequest request) {
        return ResponseEntity.ok(taskService.createTask(userId, request));
    }

    // 특정 날짜의 할 일 목록 조회
    // GET /api/task?date=2026-08-19
    @GetMapping
    public ResponseEntity<List<TaskResponse>> getTasksByDate(
            @CurrentUser Long userId,
            @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return ResponseEntity.ok(taskService.getTasksByDate(userId, date));
    }

    // 할 일 일정 이동
    // PATCH /api/task/1/schedule
    @PatchMapping("/{taskId}/schedule")
    public ResponseEntity<TaskResponse> updateSchedule(
            @CurrentUser Long userId,
            @PathVariable("taskId") Long taskId,
            @RequestBody TaskScheduleRequest request) {
        return ResponseEntity.ok(taskService.updateSchedule(userId, taskId, request));
    }

    // 할 일 삭제
    // DELETE /api/task/1
    @DeleteMapping("/{taskId}")
    public ResponseEntity<Void> deleteTask(
            @CurrentUser Long userId,
            @PathVariable("taskId") Long taskId) {
        taskService.deleteTask(userId, taskId);
        return ResponseEntity.noContent().build();
    }

    // 완료 여부 체크/해제
    // PATCH /api/task/1/toggle
    @PatchMapping("/{taskId}/toggle")
    public ResponseEntity<TaskResponse> toggleComplete(
            @CurrentUser Long userId,
            @PathVariable("taskId") Long taskId) {
        return ResponseEntity.ok(taskService.toggleComplete(userId, taskId));
    }

}
