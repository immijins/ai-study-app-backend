package org.example.backend.domain.controller;

import lombok.RequiredArgsConstructor;
import org.example.backend.domain.entity.Todo;
import org.example.backend.domain.repository.TodoRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/todos")
@RequiredArgsConstructor
public class TodoController {
    private final TodoRepository todoRepository;

    // 1. 전체 목록 조회
    @GetMapping
    public List<Todo> getTodos() {
        return todoRepository.findAll();
    }

    // 2. 할일 추가
    @PostMapping
    public Todo createTodo(@RequestBody Todo todo) {
        return todoRepository.save(todo);
    }

    // 3. 완료 상태 토글
    @PatchMapping("/{id}/toggle")
    public Todo toggleTodo(@PathVariable("id") Long id) {
        Todo todo = todoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Todo를 찾을 수 없습니다."));
        todo.setCompleted(!todo.isCompleted());
        return todoRepository.save(todo);
    }

    // 4. 삭제
    @DeleteMapping("/{id}")
    public void deleteTodo(@PathVariable("id") Long id) {
        todoRepository.deleteById(id);
    }

    // 5. 개별 조회
    @GetMapping("/{id}")
    public Todo getTodoById(@PathVariable("id") Long id) {
        return todoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Todo를 찾을 수 없습니다."));
    }
}
