package co.vuckovic.aicodereview.codereview.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "code-analysis")
@Data
public class CodeAnalysisConfig {
    private String security;
}
