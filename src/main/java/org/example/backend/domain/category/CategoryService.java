package org.example.backend.domain.category;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CategoryService {
    // 생성자 주입
    private final CategoryRepository categoryRepository;

    // 카테고리 생성
    @Transactional
    public CategoryResponse createCategory(Long userId, CategoryRequest request) {
        if (categoryRepository.existsByUserIdAndCategoryName(userId, request.getCategoryName())) {
            throw new IllegalArgumentException("이미 존재하는 카테고리명입니다: " + request.getCategoryName());
        }

        Category category = Category.builder()
                .userId(userId)
                .categoryName(request.getCategoryName())
                .build();

        return new CategoryResponse(categoryRepository.save(category));
    }

    // 카테고리 전체 조회
    public List<CategoryResponse> getCategories(Long userId) {
        return categoryRepository.findAllByUserIdOrderByIdAsc(userId).stream()
                .map(CategoryResponse::new)
                .collect(Collectors.toList());
    }

    // 카테고리 개별 조회
    public CategoryResponse getCategory(Long userId, Long categoryId) {
        Category category = categoryRepository.findByIdAndUserId(categoryId, userId)
                .orElseThrow(() -> new IllegalArgumentException("카테고리를 찾을 수 없습니다."));
        return new CategoryResponse(category);
    }

    // 카테고리 수정
    @Transactional
    public CategoryResponse updateCategory(Long userId, Long categoryId, CategoryRequest request) {
        Category category = categoryRepository.findByIdAndUserId(categoryId, userId)
                .orElseThrow(() -> new IllegalArgumentException("카테고리를 찾을 수 없습니다."));

        if (!category.getCategoryName().equals(request.getCategoryName())
            && categoryRepository.existsByUserIdAndCategoryName(userId, request.getCategoryName())) {
            throw new IllegalArgumentException("이미 존재하는 카테고리입니다." + request.getCategoryName());
        }

        category.updateName(request.getCategoryName());
        return new CategoryResponse(category);
    }

    // 카테고리 삭제
    @Transactional
    public void deleteCategory(Long userId, Long categoryId) {
        Category category = categoryRepository.findByIdAndUserId(categoryId, userId)
                .orElseThrow(() -> new IllegalArgumentException("카테고리를 찾을 수 없거나 권한이 없습니다."));

        categoryRepository.delete(category);
    }
}
