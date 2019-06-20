package com.gaborkallos.thefeedbacker;

import com.gaborkallos.thefeedbacker.model.City;
import com.gaborkallos.thefeedbacker.model.Country;
import com.gaborkallos.thefeedbacker.service.AdminService;
import com.gaborkallos.thefeedbacker.service.CityService;
import com.gaborkallos.thefeedbacker.service.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
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

    private AdminService adminService;

    @Autowired
    private CityService cityService;
    @Autowired
    private CountryService countryService;

    @Autowired
    public void setAdminService(AdminService adminService) {
        this.adminService = adminService;
        adminService.setAdministrators();
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

    @Bean
    public CommandLineRunner init() {
        return args -> {

            Country country1 = new Country(1L, "Hungary");
            Country country2 = new Country(2L, "United Kingdom");
            Country country3 = new Country(3L, "United States");

            cityService.addNewCity(new City(1L, "Budapest"));
            cityService.addNewCity(new City(2L, "London"));
            cityService.addNewCity(new City(3L, "New York"));

            countryService.addNewCountry(country1);
            countryService.addNewCountry(country2);
            countryService.addNewCountry(country3);
        };
    }

}
