package co.vuckovic.aicodereview.shared.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "chat-service")
@Data
public class ChatServiceConfig {
    private String model;
    int maxTokens;
}
