package lims.core.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class QuantumSwaggerConfig {
    /*
    apis : 대상 패키지 설정
    paths : 어떤 식으로 시작하는 api 를 보여줄것인지?
    any는 그냥 전부다. 만약, member/ 라고 설정하면 member로 시작하는 api 만 볼 수 있게 설정 가능하다.
    */
    @Bean
    public Docket restAPI() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("lims"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("QuantumLIMS 3.0 REST API")
                .version("1.0.0")
                .description("QuantumLIMS 3.0.")
                .build();
    }
}
