package org.example.backend.domain.category;

import lombok.RequiredArgsConstructor;
import org.example.backend.common.annotation.CurrentUser;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/category")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    // 카테고리 생성
    @PostMapping
    public ResponseEntity<CategoryResponse> createCategory (
        @CurrentUser Long userId,
        @RequestBody CategoryRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(categoryService.createCategory(userId, request));
    }

    // 전체 목록 조회
    @GetMapping
    public ResponseEntity<List<CategoryResponse>> getCategories(
            @CurrentUser Long userId) {
        return ResponseEntity.ok(categoryService.getCategories(userId));
    }

    // 개별 목록 조회
    @GetMapping("/{categoryId}")
    public ResponseEntity<CategoryResponse> getCategory(
            @CurrentUser Long userId,
            @PathVariable("categoryId") Long categoryId) {
        return ResponseEntity.ok(categoryService.getCategory(userId, categoryId));
    }

    // 카테고리 수정
    @PutMapping("/{categoryId}")
    public ResponseEntity<CategoryResponse> updateCategory(
            @CurrentUser Long userId,
            @PathVariable("categoryId") Long categoryId,
            @RequestBody CategoryRequest request) {
        return ResponseEntity.ok(categoryService.updateCategory(userId, categoryId, request));
    }

    // 카테고리 삭제
    @DeleteMapping("/{categoryId}")
    public ResponseEntity<Void> deleteCategory(
            @CurrentUser Long userId,
            @PathVariable Long categoryId) {
        categoryService.deleteCategory(userId, categoryId);
        return ResponseEntity.noContent().build();
    }
}
