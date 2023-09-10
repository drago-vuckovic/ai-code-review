package co.vuckovic.aicodereview.beancheckertests;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import co.vuckovic.aicodereview.codereview.config.BitbucketRepoFIleContentControllerConfig ;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class BitbucketRepoFileContentControllerConfigTest {
    @Autowired
    private BitbucketRepoFIleContentControllerConfig bitbucketRepoFIleContentControllerConfig;

    @Test
    public void testEndpoint() {
        String expectedEndpoint = "/api/bitbucket/v1";
        String actualEndpoint = bitbucketRepoFIleContentControllerConfig.getEndpoint();
        assertEquals(expectedEndpoint, actualEndpoint);
    }

    @Test
    public void testRequestHeader() {
        String expectedRequestHeader = "FilePath";
        String actualRequestHeader = bitbucketRepoFIleContentControllerConfig.getRequestHeader();
        assertEquals(expectedRequestHeader, actualRequestHeader);
    }
}
