package co.vuckovic.aicodereview.codereview.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "bitbucket.repo")
@Data
public class BitbucketRepoConfig {
    private String apiRootUrl;
}
