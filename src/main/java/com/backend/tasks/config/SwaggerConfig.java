package com.backend.tasks.config;

import com.google.common.base.Predicates;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Optional;


@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket fullApi() {

        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("API_FULL")
                .select()
                .paths(PathSelectors.any())
                .apis(RequestHandlerSelectors.any())
                .apis(Predicates.not(RequestHandlerSelectors.basePackage("org.springframework.boot")))
                .build()
                .genericModelSubstitutes(Optional.class);
    }

}
