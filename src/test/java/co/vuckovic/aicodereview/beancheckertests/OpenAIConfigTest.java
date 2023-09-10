package co.vuckovic.aicodereview.beancheckertests;

import co.vuckovic.aicodereview.shared.config.OpenAIConfig;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Disabled("This test class is currently disabled")
@SpringBootTest
public class OpenAIConfigTest {

    @Autowired
    private OpenAIConfig openAIConfig;

    @Test
    public void testKey() {

        String actualKeyValue = openAIConfig.getKey();
        String expectedKeyValue = System.getProperty("OPENAI_API_ROOT_KEY");

        assertEquals(expectedKeyValue, actualKeyValue);
    }
    @Test
    public void testUrl() {

        String actualUrlValue = openAIConfig.getUrl();
        String expectedUrlValue = System.getProperty("OPENAI_API_ROOT_URL");

        assertEquals(expectedUrlValue, actualUrlValue);
    }
}
