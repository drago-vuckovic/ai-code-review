package co.vuckovic.aicodereview.beancheckertests;

import co.vuckovic.aicodereview.codereview.config.BitbucketConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class BitbucketConfigTest {
    @Autowired
    private BitbucketConfig bitbucketConfig;

    @Test
    public void testWholeFlag() {

        boolean expectedWholeFlagValue = false;
        boolean actualWholeFlagValue = bitbucketConfig.isWholeFlag();

        assertEquals(expectedWholeFlagValue, actualWholeFlagValue);
    }
}
