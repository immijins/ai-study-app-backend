package org.example.backend.domain.category;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CategoryResponse {
    private final Long id;
    private final String categoryName;
    private final LocalDateTime createdAt;

    public CategoryResponse(Category category) {
        this.id = category.getId();
        this.categoryName = category.getCategoryName();
        this.createdAt = category.getCreatedAt();
    }
}
