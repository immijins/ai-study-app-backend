package org.example.backend.domain.task;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, Long> {
    // 특정 유저의 '특정 날짜' 할 일을 카테고리 ID 순으로 정렬해서 조회
    List<Task> findAllByUserIdAndPlanDateOrderByCategoryIdAsc(Long userId, LocalDate planDate);

    // 단건 조회용
    Optional<Task> findByIdAndUserId(Long id, Long userId);

    // 당일 완료한 할 일 개수
    @Query("SELECT new org.example.backend.domain.task.TaskStatResponse(" +
            "t.planDate, " +
            "COUNT(t), " +
            "SUM(CASE WHEN t.isComplete = true THEN 1L ELSE 0L END)) " +
            "FROM Task t " +
            "WHERE t.userId = :userId AND t.planDate >= :startDate " +
            "GROUP BY t.planDate " +
            "ORDER BY t.planDate ASC")
    List<TaskStatResponse> getDailyTaskStats(@Param("userId") Long userId, @Param("startDate") LocalDate startDate);
}
