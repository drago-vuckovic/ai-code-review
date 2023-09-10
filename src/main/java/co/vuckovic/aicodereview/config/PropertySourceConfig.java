package co.vuckovic.aicodereview.config;

import co.vuckovic.aicodereview.codereview.config.*;
import co.vuckovic.aicodereview.shared.config.ChatServiceConfig;
import co.vuckovic.aicodereview.shared.config.OpenAIConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@PropertySource(value = {
        "classpath:application.yml",
        "classpath:codeanalysisconfig/codeanalysisconfig.yml",
        "classpath:generalcodereviewconfig/generalcodereviewconfig.yml",
        "classpath:stylecheckingconfig/stylecheckingconfig.yml",
        "classpath:stringtemplatesconfig/stringtemplatesconfig.yml",
        "classpath:bitbucketconfig/bitbucketconfig.yml",
        "classpath:bitbucketreposconfig/bitbucketreposconfig.yml",
        "classpath:chatserviceconfig/chatserviceconfig.yml",
        "classpath:openaiconfig/openaiconfig.yml",
        "classpath:bitbucketconfig/repos.yml"
}, factory = MultipleYamlPropertySourceFactory.class)
@Configuration
public class PropertySourceConfig {

    @Bean
    public CodeAnalysisConfig codeAnalysisConfig() {
        return new CodeAnalysisConfig();
    }

    @Bean
    public GeneralCodeReviewConfig generalCodeReviewConfig() {
        return new GeneralCodeReviewConfig();
    }

    @Bean
    public StringTemplatesConfig stringTemplatesConfig() {
        return new StringTemplatesConfig();
    }
    @Bean
    public StyleCheckingConfig styleCheckingConfig() {
        return new StyleCheckingConfig();
    }
    @Bean
    public BitbucketConfig bitbucketConfig() {
        return new BitbucketConfig();
    }
    @Bean
    public BitbucketRepoConfig bitbucketRepoConfig() {
        return new BitbucketRepoConfig();
    }

    @Bean
    public BitbucketRepoFIleContentControllerConfig bitbucketRepoFIleContentControllerConfig() {
        return new BitbucketRepoFIleContentControllerConfig();
    }

    @Bean
    public ChatServiceConfig chatServiceConfig() {
        return new ChatServiceConfig();
    }

    @Bean
    public OpenAIConfig openAIConfig() {
        return new OpenAIConfig();
    }
}
