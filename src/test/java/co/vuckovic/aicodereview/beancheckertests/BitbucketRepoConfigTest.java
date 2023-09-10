package co.vuckovic.aicodereview.beancheckertests;


import co.vuckovic.aicodereview.codereview.config.BitbucketRepoConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class BitbucketRepoConfigTest {

    @Autowired
    private BitbucketRepoConfig bitbucketRepoConfig;

    @Test
    public void testApiRootUrl() {
        String expectedApiRootUrl = "https://api.bitbucket.org/2.0/repositories";
        String actualApiRootUrl = bitbucketRepoConfig.getApiRootUrl();

        assertEquals(expectedApiRootUrl, actualApiRootUrl);
    }

}
