package co.vuckovic.aicodereview.codereview.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "bitbucket.repo.file-content-controller")
@Data
public class BitbucketRepoFIleContentControllerConfig {
    private String endpoint;
    private String requestHeader;

}
