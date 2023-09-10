package co.vuckovic.aicodereview.beancheckertests;

import co.vuckovic.aicodereview.codereview.config.CodeAnalysisConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class CodeAnalysisConfigTest {
    @Autowired
    CodeAnalysisConfig codeAnalysisConfig;

    @Test
    public void testSecurity() {
        String expectedSecurityValue = "Check for security vulnerabilities of the following code and output only the important vulnerabilities. Output should be in the following format: Line <number>: <Rate the importance of the check from 1 to 5, 1 has least importance and 5 has highest importance>: <Description of the vulnerability> \n\n";
        String actualSecurityValue = codeAnalysisConfig.getSecurity();

        assertEquals(expectedSecurityValue, actualSecurityValue);
    }
}
