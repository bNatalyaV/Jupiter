package id.bnv.jupiter;

import id.bnv.jupiter.authentication.Authentication;
import id.bnv.jupiter.authentication.IssueAndDecodeToken;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public Docket swaggerSettings() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("id.bnv.jupiter"))
                .build();
    }

    @Bean
    public IssueAndDecodeToken issueAndDecodeToken() {
        return new IssueAndDecodeToken();
    }
}

