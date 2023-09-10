package co.vuckovic.aicodereview.beancheckertests;

import co.vuckovic.aicodereview.codereview.config.StringTemplatesConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class StringTemplatesConfigTest {

    @Autowired
    private StringTemplatesConfig stringTemplatesConfig;

    @Test
    public void testStringTemplates() {

        String expectedStrippedText = "Strip text and leave only code with comments of the following input:\n";
        String actualStrippedText = stringTemplatesConfig.getStripTextFromCodeTemplate();

        assertEquals(expectedStrippedText, actualStrippedText);
    }
}
