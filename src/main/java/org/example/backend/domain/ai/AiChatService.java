package org.example.backend.domain.ai;


import org.example.backend.domain.task.TaskService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.node.ArrayNode;
import tools.jackson.databind.node.ObjectNode;

@Service
public class AiChatService {

    private final RestClient restClient;
    private final ObjectMapper objectMapper;
    private final TaskService taskService;

    @Value("${openai.model:gpt-4o-mini}")
    private String model;

    public AiChatService(
            @Value("${openai.api-key}") String apiKey,
            ObjectMapper objectMapper,
            TaskService taskService) {
        this.objectMapper = objectMapper;
        this.taskService = taskService;
        this.restClient = RestClient.builder()
                .baseUrl("https://api.openai.com/v1")
                .defaultHeader("Authorization", "Bearer " + apiKey)
                .defaultHeader("Content-Type", "application/json")
                .build();
    }

    public String chatAndAddTodo(Long userId, String userMessage) {
        // 1. 대화 기록과 사용할 함수(도구) 정의
        ArrayNode messages = objectMapper.createArrayNode();
        messages.add(createMessage("system", "너는 친절한 스터디 플래너야. 사용자가 공부 계획을 추가해달라고 하거나 동의하면 'add_study_plan' 도구를 사용해."));
        messages.add(createMessage("user", userMessage));

        ArrayNode tools = objectMapper.createArrayNode();
        tools.add(createToolDefinition());

        // 2. OpenAI 1차 호출
        JsonNode firstResponse = callOpenAi(messages, tools);
        JsonNode firstMessage = firstResponse.path("choices").get(0).path("message");

        // 3. AI가 '할 일 추가' 함수를 쓰겠다고 응답했는지 확인
        if (firstMessage.has("tool_calls")) {
            messages.add(firstMessage);

            for (JsonNode toolCall : firstMessage.path("tool_calls")) {
                String functionName = toolCall.path("function").path("name").asText();
                String argumentsJson = toolCall.path("function").path("arguments").asText();

                if ("add_study_plan".equals(functionName)) {
                    try {
                        JsonNode args = objectMapper.readTree(argumentsJson);
                        String taskTitle = args.path("title").asText();

                        // 백엔드 DB 저장 로직 호출 (TaskService 사용)
                        taskService.addAiRecommendedTask(userId, taskTitle);

                        // DB 저장 성공 결과를 OpenAI에게 전달
                        ObjectNode toolResult = objectMapper.createObjectNode();
                        toolResult.put("role", "tool");
                        toolResult.put("tool_call_id", toolCall.path("id").asText());
                        toolResult.put("content", "성공적으로 추가됨. 카테고리 ID 2번으로 저장 완료.");
                        messages.add(toolResult);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            // 4. 실행 결과를 바탕으로 최종 응답 생성 (2차 호출)
            JsonNode secondResponse = callOpenAi(messages, null);
            return secondResponse.path("choices").get(0).path("message").path("content").asText();
        }

        // 일반적인 대화인 경우 단순 텍스트 반환
        return firstMessage.path("content").asText();
    }

    private JsonNode callOpenAi(ArrayNode messages, ArrayNode tools) {
        ObjectNode requestBody = objectMapper.createObjectNode();
        requestBody.put("model", model);
        requestBody.set("messages", messages);
        if (tools != null) {
            requestBody.set("tools", tools);
            requestBody.put("tool_choice", "auto"); // AI가 판단해서 함수를 쓸지 말지 결정
        }

        return restClient.post()
                .uri("/chat/completions")
                .body(requestBody)
                .retrieve()
                .body(JsonNode.class);
    }

    private ObjectNode createMessage(String role, String content) {
        ObjectNode msg = objectMapper.createObjectNode();
        msg.put("role", role);
        msg.put("content", content);
        return msg;
    }

    private ObjectNode createToolDefinition() {
        ObjectNode tool = objectMapper.createObjectNode();
        tool.put("type", "function");
        ObjectNode function = tool.putObject("function");
        function.put("name", "add_study_plan");
        function.put("description", "사용자의 할 일 목록에 공부 계획을 추가합니다.");

        ObjectNode parameters = function.putObject("parameters");
        parameters.put("type", "object");
        ObjectNode properties = parameters.putObject("properties");

        ObjectNode titleProp = properties.putObject("title");
        titleProp.put("type", "string");
        titleProp.put("description", "공부 계획 내용 (예: '리액트 훅 복습')");

        parameters.putArray("required").add("title");
        return tool;
    }

}
