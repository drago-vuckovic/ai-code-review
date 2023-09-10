package co.vuckovic.aicodereview.shared.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenAPIConfig {

    @Value("${swagger-ui.server-prod}")
    String apiUrlProd;

    @Value("${swagger-ui.server-prod}")
    String apiUrlDev;

    @Bean
    public OpenAPI myOpenAPI() {
        Server devServer = new Server();
        devServer.setUrl(apiUrlDev);
        devServer.setDescription("Server URL in Development environment");

        Server prodServer = new Server();
        prodServer.setUrl(apiUrlProd);
        prodServer.setDescription("Server URL in Production environment");

        Contact contact = new Contact();
        contact.setEmail("drago@vuckovic.co");
        contact.setName("Drago Vuckovic");
        contact.setUrl("vuckovic.co");

        License mitLicense = new License().name("vuckovic.co").url("vuckovic.co");

        Info info = new Info()
                .title("Bitbucket API")
                .version("1.0")
                .contact(contact)
                .description("This API exposes endpoints to manage Bitbucket repos.")
                .termsOfService("vuckovic.co")
                .license(mitLicense);

        return new OpenAPI().info(info).servers(List.of(devServer, prodServer));
    }
}
