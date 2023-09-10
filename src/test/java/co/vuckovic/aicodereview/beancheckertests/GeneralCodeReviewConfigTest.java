package co.vuckovic.aicodereview.beancheckertests;

import co.vuckovic.aicodereview.codereview.config.GeneralCodeReviewConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class GeneralCodeReviewConfigTest {

    @Autowired
    private GeneralCodeReviewConfig generalCodeReviewConfig;

    @Test
    public void testCheck () {

        String expectedCheckValue = "Make code review of the following code and output only the important notes. Output should be in the following format: Line <number>:<Rate the importance of the check from 1 to 5, 1 has least importance and 5 has highest importance>:<Description> \n\n";
        String actualCheckValue = generalCodeReviewConfig.getCheck();

        assertEquals(expectedCheckValue, actualCheckValue);
    }

    @Test
    public void testLineNumbers () {

        String expectedLineNumbersValue = "When making Code Review, please include the line numbers";
        String actualLineNumbersValue = generalCodeReviewConfig.getLineNumbers();

        assertEquals(expectedLineNumbersValue, actualLineNumbersValue);
    }

    @Test
    public void testImportanceRateThreshold () {

        int expectedImportanceRateThreshold = 4;
        int actualImportanceRateThreshold = generalCodeReviewConfig.getImportanceRateThreshold();

        assertEquals(expectedImportanceRateThreshold, actualImportanceRateThreshold);
    }
}
