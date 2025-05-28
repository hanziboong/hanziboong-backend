package skhu.hanziboong.global.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;

@Configuration
public class SwaggerConfig {

    @Value("${swagger.server.url}")
    private String swaggerServerUrl;

    @Bean
    public OpenAPI createOpenApi() {
        Server server = new Server();
        server.setUrl(swaggerServerUrl);

        return new OpenAPI()
                .info(getInfo())
                .servers(List.of(server))
                .components(new Components().addSecuritySchemes("bearerAuth", getSecurityScheme()))
                .addSecurityItem(new SecurityRequirement().addList("bearerAuth"));
    }

    private Info getInfo() {
        return new Info()
                .title("Hanziboong API")
                .description("Hanziboong 서비스의 API입니다.")
                .version("0.1");
    }

    private SecurityScheme getSecurityScheme() {
        return new SecurityScheme().type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT")
                .in(SecurityScheme.In.HEADER)
                .name(HttpHeaders.AUTHORIZATION);
    }
}
