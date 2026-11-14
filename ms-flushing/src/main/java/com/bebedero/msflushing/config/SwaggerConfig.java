
package com.bebedero.msflushing.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI flushingOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Bebedero - Microservicio de Flushing")
                        .version("1.0.0")
                        .description(
                                "API REST para gestionar los registros " +
                                        "de limpieza de líneas de bebederos. " +
                                        "Permite crear, consultar, actualizar " +
                                        "y eliminar flushings."
                        ));
    }
}
