package co.vuckovic.aicodereview.beancheckertests;

import co.vuckovic.aicodereview.codereview.config.StyleCheckingConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class StyleCheckingConfigTest {
    @Autowired
    private StyleCheckingConfig styleCheckingConfig;

    @Test
    public void testStyleChecking() {
        String expectedCheckStyle = "Check code styling of the following code and output only the important notes. Output should be in the following format: Line <number>: <Rate the importance of the check from 1 to 5, 1 has least importance and 5 has highest importance>: <Description> \n\n";
        String actualCheckStyle = styleCheckingConfig.getCheckStyle();

        assertEquals(expectedCheckStyle, actualCheckStyle);

    }
}
