package org.example.backend.domain.task;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, Long> {
    // 특정 유저의 '특정 날짜' 할 일을 카테고리 ID 순으로 정렬해서 조회
    List<Task> findAllByUserIdAndPlanDateOrderByCategoryIdAsc(Long userId, LocalDate planDate);

    // 단건 조회용
    Optional<Task> findByIdAndUserId(Long id, Long userId);
}
