package com.cognibank.usermng.usermngspringmicroserviceapp.config;

import com.cognibank.usermng.usermngspringmicroserviceapp.controller.UserController;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static springfox.documentation.builders.PathSelectors.regex;

@Configuration
@EnableSwagger2
public class SwaggerConfig extends WebMvcConfigurationSupport {
    @Bean
    public Docket userApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.cognibank.usermng.usermngspringmicroserviceapp.controller"))
                .paths(regex("/.*"))
                .build()
                .apiInfo(metaData());
    }

<<<<<<< Updated upstream
    private ApiInfo metaData() {
        return new ApiInfoBuilder()
                .title("Cogni-Bank User REST API")
                .description("User API for Cogni-Bank project.")
                .version(UserController.VERSION)
=======
    public Docket api() {

        return new Docket(DocumentationType.SWAGGER_2).select()

                .apis(RequestHandlerSelectors

                        .basePackage("com.cognibank.usermng.usermngspringmicroserviceapp.controller"))

                .paths(PathSelectors.regex("/.*"))

>>>>>>> Stashed changes
                .build();
    }

    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");

        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }
}