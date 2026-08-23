package org.example.backend.domain.dday;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DdayService {
    // 생성자 주입
    private final DdayRepository ddayRepository;

    // 디데이 생성
    @Transactional
    public DdayResponse createDday(Long userId, DdayRequest request) {
        if (ddayRepository.existsByUserIdAndTitle(userId, request.getTitle())) {
            throw new IllegalArgumentException("이미 존재하는 디데이입니다:" + request.getTitle());
        }

        Dday dday = Dday.builder()
                .userId(userId)
                .title(request.getTitle())
                .dayDate(request.getDayDate())
                .build();

        return new DdayResponse(ddayRepository.save(dday));
    }

    // 디데이 전체
    public List<DdayResponse> getDdayList(Long userId) {
        return ddayRepository.findAllByUserIdOrderByIdAsc(userId).stream()
                .map(DdayResponse::new)
                .collect(Collectors.toList());
    }

    // 디데이 개별 조회
    public DdayResponse getDday(Long userId, Long dayId) {
        Dday dday = ddayRepository.findByIdAndUserId(dayId, userId)
                .orElseThrow(() -> new IllegalArgumentException("디데이를 찾을 수 없습니다."));
        return new DdayResponse(dday);
    }

    // 디데이 수정
    @Transactional
    public DdayResponse updateDday(Long userId, Long dayId, DdayRequest request) {
        Dday dday = ddayRepository.findByIdAndUserId(dayId, userId)
                .orElseThrow(() -> new IllegalArgumentException("디데이를 찾을 수 없습니다."));

        if (!dday.getTitle().equals(request.getTitle())
                && ddayRepository.existsByUserIdAndTitle(userId, request.getTitle())) {
            throw new IllegalArgumentException("이미 존재하는 디데이입니다." + request.getTitle());
        }

        dday.updateDday(request.getTitle(), request.getDayDate());
        return new DdayResponse(dday);
    }

    // 디데이 삭제
    @Transactional
    public void deleteDday(Long userId, Long dayId) {
        Dday dday = ddayRepository.findByIdAndUserId(dayId, userId)
                .orElseThrow(() -> new IllegalArgumentException("디데이를 찾을 수 없습니다."));

        ddayRepository.delete(dday);
    }
}
