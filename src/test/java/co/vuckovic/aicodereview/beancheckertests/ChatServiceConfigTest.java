package co.vuckovic.aicodereview.beancheckertests;

import co.vuckovic.aicodereview.shared.config.ChatServiceConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class ChatServiceConfigTest {

    @Autowired
    private ChatServiceConfig chatServiceConfig;

    @Test
    public void testModel() {
        String  expectedWholeFlagValue = "gpt-3.5-turbo-16k";
        String  actualWholeFlagValue = chatServiceConfig.getModel();

        assertEquals(expectedWholeFlagValue, actualWholeFlagValue);
    }
    @Test
    public void testMaxTokens() {
        int expectedWholeFlagValue = 500;
        int actualWholeFlagValue = chatServiceConfig.getMaxTokens();

        assertEquals(expectedWholeFlagValue, actualWholeFlagValue);
    }
}
