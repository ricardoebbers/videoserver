package com.ebbers.challenge.videoserver.infrastructure.security

import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.security.SecurityScheme
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SwaggerConfiguration {
    @Bean
    fun openAPI(): OpenAPI {
        return OpenAPI()
                .components(Components().addSecuritySchemes("basicScheme",
                        SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("basic")))
    }
}
