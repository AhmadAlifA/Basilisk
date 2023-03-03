package com.basilisk;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfiguration {

    @Bean
    public OpenAPI basiliskOpenApi(){

        String schemeName = "bearerAuth";

        SecurityRequirement requirement = new SecurityRequirement().addList(schemeName);

        SecurityScheme scheme = new SecurityScheme()
                .name(schemeName)
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT");

        Components jwtComponent = new Components().addSecuritySchemes(schemeName, scheme);

        Info info = new Info()
                .title("Basilisk API Documentation")
                .description("Contoh deskripsi dokumentasi swagger untuk aplikasi Basilisk kita.")
                .version("v.1.0.0");    //        versi untuk API Basilisk bukan untuk keseluruhan Basilisk

        var openAPI = new OpenAPI()
                .info(info)
                .addSecurityItem(requirement)
                .components(jwtComponent);

        return  openAPI;
    }


}

