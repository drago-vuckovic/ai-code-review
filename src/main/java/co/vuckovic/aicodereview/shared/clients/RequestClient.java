package co.vuckovic.aicodereview.shared.clients;

import co.vuckovic.aicodereview.shared.dtos.requests.ChatRequestOpenAI;
import co.vuckovic.aicodereview.shared.dtos.responses.ChatCompletionResponse;
import feign.Headers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

//@FeignClient(name = "chat-api", url = "${openai.root.api.url}")
@FeignClient(name = "chat-api", url = "https://api.openai.com/v1/chat/completions")
public interface RequestClient {

    @PostMapping("")
    @Headers({"Content-Type: application/json"})
    ChatCompletionResponse chatOpenAI(
            @RequestBody ChatRequestOpenAI chatRequestOpenAI,
            @RequestHeader("Authorization") String apiKey);

}