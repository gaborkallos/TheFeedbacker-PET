package com.gaborkallos.thefeedbacker;

import com.gaborkallos.thefeedbacker.service.SystemAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
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

}
