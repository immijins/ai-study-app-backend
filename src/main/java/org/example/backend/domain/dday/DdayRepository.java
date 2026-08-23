package org.example.backend.domain.dday;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DdayRepository extends JpaRepository<Dday, Long> {
    List<Dday> findAllByUserIdOrderByIdAsc(Long userID);
    Optional<Dday> findByIdAndUserId(Long id, Long userId);
    boolean existsByUserIdAndTitle(Long userId, String title);
}
