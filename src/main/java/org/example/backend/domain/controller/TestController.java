package org.example.backend.domain.controller;

import lombok.RequiredArgsConstructor;
import org.example.backend.domain.entity.TestUser;
import org.example.backend.domain.repository.TestUserRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/test")
@RequiredArgsConstructor
public class TestController {
    private final TestUserRepository testUserRepository;

    // 1. DB에 테스트 데이터 저장
    @PostMapping("/user")
    public TestUser createUser(@RequestParam String name, @RequestParam String email) {
        TestUser user = new TestUser(name, email);
        return testUserRepository.save(user);
    }

    // 2. DB에 저장된 전체 데이터 조회
    @GetMapping("/users")
    public List<TestUser> getAllUsers() {
        return testUserRepository.findAll();
    }
}

