package co.vuckovic.aicodereview.codereview.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "string-templates")
@Data
public class StringTemplatesConfig {
    private String stripTextFromCodeTemplate;

}
