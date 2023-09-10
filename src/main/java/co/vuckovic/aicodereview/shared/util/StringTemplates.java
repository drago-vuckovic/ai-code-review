package co.vuckovic.aicodereview.shared.util;

import co.vuckovic.aicodereview.codereview.config.StyleCheckingConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import co.vuckovic.aicodereview.codereview.config.StringTemplatesConfig;
import co.vuckovic.aicodereview.codereview.config.GeneralCodeReviewConfig;
import co.vuckovic.aicodereview.codereview.config.CodeAnalysisConfig;


@Component
@RequiredArgsConstructor
public class StringTemplates {

    private final StringTemplatesConfig stringTemplatesConfig;

    private final GeneralCodeReviewConfig generalCodeReviewConfig;

    private final CodeAnalysisConfig codeAnalysisConfig;

    private final StyleCheckingConfig styleCheckingConfig;


    public String stripTextFromCodeTemplate(String code) {
        String template = stringTemplatesConfig.getStripTextFromCodeTemplate();

        if (template != null) {
            StringBuilder sb = new StringBuilder();
            sb.append(template).append(code);
            return sb.toString();
        }

        return code;
    }

    public String includeLineNumbers() {
        return generalCodeReviewConfig.getLineNumbers();
    }

    public String securityCheck() {
        return codeAnalysisConfig.getSecurity();
    }

    public String codeStyleCheck() {
        return styleCheckingConfig.getCheckStyle();
    }

    public String generalCodeReview() {
        return generalCodeReviewConfig.getCheck();
    }
}

