package org.example.backend.domain.dday;

import lombok.RequiredArgsConstructor;
import org.example.backend.common.annotation.CurrentUser;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/dday")
@RequiredArgsConstructor
public class DdayController {

    private final DdayService ddayService;

    // 디데이 등록
    // POST /api/dday
    @PostMapping
    public ResponseEntity<DdayResponse> createDday(
            @CurrentUser Long userId,
            @RequestBody DdayRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ddayService.createDday(userId, request));
    }

    // 전체 목록 조회
    @GetMapping
    public ResponseEntity<List<DdayResponse>> getDdayList(
            @CurrentUser Long userId) {
        return ResponseEntity.ok(ddayService.getDdayList(userId));
    }

    // 개별 목록 조회
    @GetMapping("/{dayId}")
    public ResponseEntity<DdayResponse> getDday(
            @CurrentUser Long userId,
            @PathVariable("dayId") Long dayId) {
        return ResponseEntity.ok(ddayService.getDday(userId, dayId));
    }

    // 디데이 수정
    @PutMapping("/{dayId}")
    public ResponseEntity<DdayResponse> updateDday(
            @CurrentUser Long userId,
            @PathVariable("dayId") Long dayId,
            @RequestBody DdayRequest request) {
        return ResponseEntity.ok(ddayService.updateDday(userId, dayId, request));
    }

    // 디데이 삭제
    @DeleteMapping("/{dayId}")
    public ResponseEntity<Void> deleteDday(
            @CurrentUser Long userId,
            @PathVariable Long dayId) {
        ddayService.deleteDday(userId, dayId);
        return ResponseEntity.noContent().build();
    }

}
