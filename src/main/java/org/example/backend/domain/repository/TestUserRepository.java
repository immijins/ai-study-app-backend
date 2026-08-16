package org.example.backend.domain.repository;

import org.example.backend.domain.entity.TestUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestUserRepository extends JpaRepository<TestUser, Long> {
}
