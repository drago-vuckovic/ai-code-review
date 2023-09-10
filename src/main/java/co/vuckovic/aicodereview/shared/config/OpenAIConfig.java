package co.vuckovic.aicodereview.shared.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "openai.root.api")
@Data
public class OpenAIConfig {
    private String key;
    private String url;
}
