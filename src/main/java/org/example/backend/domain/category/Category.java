package org.example.backend.domain.category;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "category")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Users 테이블의 id는 조회용으로만 사용되어 객체형이 아닌 Long형으로 사용(직관적)
    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "category_name" ,nullable = false, length = 100)
    private String categoryName;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    public void perPersist() {
        this.createdAt = LocalDateTime.now();
    }

    @Builder
    public Category(Long userId, String categoryName) {
        this.userId = userId;
        this.categoryName = categoryName;
    }

    // 카테고리명 수정 메서드
    public void updateName(String categoryName) {
        this.categoryName = categoryName;
    }
}
