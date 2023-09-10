package co.vuckovic.aicodereview.shared.services;

import co.vuckovic.aicodereview.shared.clients.RequestClient;
import co.vuckovic.aicodereview.shared.config.ChatServiceConfig;
import co.vuckovic.aicodereview.shared.config.OpenAIConfig;
import co.vuckovic.aicodereview.shared.dtos.requests.ChatRequestOpenAI;
import co.vuckovic.aicodereview.shared.dtos.requests.MessageRole;
import co.vuckovic.aicodereview.shared.dtos.responses.ChatCompletionResponse;
import co.vuckovic.aicodereview.shared.dtos.responses.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class ChatService {
    private final RequestClient requestClient;
    private final ChatServiceConfig chatServiceConfig;
    private final OpenAIConfig openAIConfig;

    public ChatCompletionResponse chatOpenAI(String role, String content) {

        Message message = Message.builder()
                .role(MessageRole.USER.getValue())
                .content(content).build();

        ChatRequestOpenAI requestBody = ChatRequestOpenAI.builder()
                .model(chatServiceConfig.getModel())
                .messages(Collections.singletonList(message))
                .max_tokens(chatServiceConfig.getMaxTokens())
                .build();

        return requestClient.chatOpenAI(requestBody, openAIConfig.getKey());
    }
}

