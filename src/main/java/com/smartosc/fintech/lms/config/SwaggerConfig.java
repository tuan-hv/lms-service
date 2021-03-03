package com.smartosc.fintech.lms.config;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

@Configuration
@EnableCaching
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("LMS-Service")
                .apiInfo(apiInfo())
                .enable(true)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.smartosc.fintech.lms.controller"))
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfo("LMS#lms-service APIs",
                "SmartOSC fintech | Lending | LMS | All PoC functions Services", "1.0.0-dev",
                "https://developer.smartosc.com/policy", null, "Copyright of SmartOSC Fintech",
                "https://developer.smartosc.com/license", Collections.emptyList());
    }

    /* enabling swagger-ui part for visual documentation */
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
    }

}
