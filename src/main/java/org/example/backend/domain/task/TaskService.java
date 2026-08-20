package org.example.backend.domain.task;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TaskService {

    private final TaskRepository taskRepository;

    // 할 일 생성
    @Transactional
    public TaskResponse createTask(Long userId, TaskRequest request) {
        Task task = Task.builder()
                .userId(userId)
                .title(request.getTitle())
                .planDate(request.getPlanDate())
                .categoryId(request.getCategoryId())
                .build();

        return new TaskResponse(taskRepository.save(task));
    }

    // 특정 날짜의 할 일 목록 조회(카테고리순 정렬)
    @Transactional
    public List<TaskResponse> getTasksByDate(Long userId, LocalDate date) {
        return taskRepository.findAllByUserIdAndPlanDateOrderByCategoryIdAsc(userId, date)
                .stream()
                .map(TaskResponse::new)
                .collect(Collectors.toList());
    }

    // 할 일 일정 변경
    @Transactional
    public TaskResponse updateSchedule(Long userId, Long taskId, TaskScheduleRequest request) {
        Task task = taskRepository.findByIdAndUserId(taskId, userId)
                .orElseThrow(() -> new IllegalArgumentException("할 일을 찾을 수 없거나 권한이 없습니다."));

        // 엔티티에 만들어둔 메서드 호출
        task.updateSchedule(request.getPlanDate(), request.getCategoryId());

        return new TaskResponse(task);
    }

    // 할 일 완료 상태 토글
    @Transactional
    public TaskResponse toggleComplete(Long userId, Long taskId) {
        Task task = taskRepository.findByIdAndUserId(taskId, userId)
                .orElseThrow(() -> new IllegalArgumentException("할 일을 찾을 수 없거나 권한이 없습니다."));

        task.toggleComplete();

        return new TaskResponse(task);
    }

    // 할 일 삭제
    @Transactional
    public void deleteTask(Long userId, Long taskId) {
        Task task = taskRepository.findByIdAndUserId(taskId, userId)
                .orElseThrow(() -> new IllegalArgumentException("할 일을 찾을 수 없거나 권한이 없습니다."));
        taskRepository.delete(task);
    }
}

