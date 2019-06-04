package com.gaborkallos.thefeedbacker;

import com.gaborkallos.thefeedbacker.service.SystemAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class TheFeedbackerApplication {

    private SystemAdminService systemAdminService;

    @Autowired
    public void setSystemAdminService(SystemAdminService systemAdminService) {
        this.systemAdminService = systemAdminService;
        systemAdminService.setAdministrators();
    }

    public static void main(String[] args) {
        SpringApplication.run(TheFeedbackerApplication.class, args);
    }

    @Bean
    public Docket swaggerApi() {
        // http://localhost:5000/swagger-ui.html
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build();
    }

}
