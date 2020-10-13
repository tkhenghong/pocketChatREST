package com.pocketchat.server.configurations.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Configuration for setting up Swagger2.
 * https://www.baeldung.com/swagger-2-documentation-for-spring-rest-api
 * https://dzone.com/articles/spring-boot-and-swagger-documenting-restful-servic
 */
@Configuration
@EnableSwagger2
public class SpringFoxConfig {

    public static final Contact defaultContact = new Contact("Teoh Kheng Hong", "https://github.com/tkhenghong", "tkhenghong@gmail.com");
    public static final ApiInfo defaultApiInfo = new ApiInfo("PocketChat REST",
            "REST controllers for PocketChat", "1.0.0", "urn.tos", defaultContact, "Apache 2.0",
            "http://www.apache.org/licenses/LICENSE-2.0", Collections.emptyList());

    private static final Set<String> defaultProducesAndConsumes = new HashSet<>(Arrays.asList(MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE));

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(defaultApiInfo)
                .produces(defaultProducesAndConsumes)
                .consumes(defaultProducesAndConsumes)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build();
    }
}
