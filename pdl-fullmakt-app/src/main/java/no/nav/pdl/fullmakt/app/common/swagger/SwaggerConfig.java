package no.nav.pdl.fullmakt.app.common.swagger;

import com.google.common.base.Predicates;
import no.nav.pdl.fullmakt.app.common.Globals;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static springfox.documentation.builders.RequestHandlerSelectors.basePackage;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("pdl-fullmakt-api")
                .tags(new Tag("Fullmakt", "Vise/Endre fullmakt"))
                .apiInfo(apiInfo())
                .useDefaultResponseMessages(false)
                .globalOperationParameters(globalOperations())
                .select()
                .apis(Predicates.or(basePackage("no.nav.pdl.fullmakt.app.fullmakt")
                ))
                .paths(PathSelectors.any())
                .build()
                .consumes(jsonMediaType())
                .produces(jsonMediaType());
    }

    private List<Parameter> globalOperations() {
        return List.of(new ParameterBuilder()
                        .name("Authorization")
                        .description("OIDC token")
                        .modelRef(new ModelRef("string"))
                        .parameterType("header")
                        .required(true)
                        .build(),
                new ParameterBuilder()
                        .name(Globals.NAV_CONSUMER_TOKEN)
                        .description("Consumer Token from STS")
                        .modelRef(new ModelRef("string"))
                        .parameterType("header")
                        .required(true)
                        .build(),
                new ParameterBuilder()
                        .name(Globals.NAV_CALL_ID)
                        .description("CallId")
                        .modelRef(new ModelRef("string"))
                        .parameterType("header")
                        .required(false)
                        .build());
    }

    private Set<String> jsonMediaType() {
        Set<String> contentType = new HashSet<>();
        contentType.add(MediaType.APPLICATION_JSON_VALUE);
        return contentType;
    }


    private ApiInfo apiInfo() {
        return new ApiInfo(
                "PDL Fullmakt",
                "Rest API for getting and posting information on PDL Fullmakt.",
                "1.0",
                "Terms of service",
                new Contact("NAV", "www.nav.no", "post@nav.no"),
                "License of API", "API license URL", Collections.emptyList());
    }
}
