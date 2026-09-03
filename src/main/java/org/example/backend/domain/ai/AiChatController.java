package org.example.backend.domain.ai;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/ai")
public class AiChatController {

    private final AiChatService aiChatService;

    public AiChatController(AiChatService aiChatService) {
        this.aiChatService = aiChatService;
    }

    // userId를 Long으로 매핑
    public record ChatRequest(Long userId, String message) {}
    public record ChatResponse(String reply) {}

    @PostMapping("/chat")
    public ChatResponse chat(@RequestBody ChatRequest request) {
        String aiReply = aiChatService.chatAndAddTodo(request.userId(), request.message());
        return new ChatResponse(aiReply);
    }
}
