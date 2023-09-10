package co.vuckovic.aicodereview.codereview.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "general-code-review")
@Data
public class GeneralCodeReviewConfig {
    private String check;
    private String lineNumbers;
    private int importanceRateThreshold;
}
