package com.yeyintlwin.musicsstore.api.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Musics Store API")
                        .version("1.0.0")
                        .description(
                                "RESTful API for the Musics Store application. " +
                                        "Provides full CRUD operations for Music tracks, Artists, Albums, Genres, and Countries. "
                                        +
                                        "All list endpoints support optional offset-based pagination and keyword search.")
                        .contact(new Contact()
                                .name("Musics Store")
                                .email("support@musicsstore.example.com"))
                        .license(new License()
                                .name("MIT License")
                                .url("https://opensource.org/licenses/MIT")))
                .servers(List.of(
                        new Server().url("http://localhost:8080").description("Local Development Server")));
    }
}
