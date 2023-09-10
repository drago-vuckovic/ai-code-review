package co.vuckovic.aicodereview.codereview.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "style-checking")
@Data
public class StyleCheckingConfig {
    private String checkStyle;
}
