package org.example.backend.domain.studyTime;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface StudyTimeRepository extends JpaRepository<StudyTime, Long> {
    // 특정 유저의 특정 날짜 공부 시간 합계 계산
    @Query("SELECT COALESCE(SUM(s.durationSeconds), 0) FROM StudyTime s WHERE s.userId = :userId And s.studyDate = :date")
    Integer getTotalStudySecondsByDate(@Param("userId") Long userId, @Param("date")LocalDate date);

    // 날짜별 공부 시간 합계
    @Query("SELECT new org.example.backend.domain.studyTime.HeatmapResponse(s.studyDate, SUM(s.durationSeconds))" +
            "FROM StudyTime s " +
            "WHERE s.userId = :userId AND s.studyDate >= :startDate " +
            "GROUP BY s.studyDate " +
            "ORDER BY s.studyDate ASC")
    List<HeatmapResponse> getHeatmapData(@Param("userId") Long userId, @Param("startDate") LocalDate startDate);
}

