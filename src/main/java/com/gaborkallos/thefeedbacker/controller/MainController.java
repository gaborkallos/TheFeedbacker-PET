package com.gaborkallos.thefeedbacker.controller;

import com.gaborkallos.thefeedbacker.model.SystemAdmin;
import com.gaborkallos.thefeedbacker.service.SystemAdminService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {

    private static final Logger logger = LoggerFactory.getLogger(ShopController.class);

    SystemAdminService systemAdminService;

    @Autowired
    public void setSystemAdminService(SystemAdminService systemAdminService) {
        this.systemAdminService = systemAdminService;
    }

    @GetMapping("/systemadmin/")
    public ResponseEntity<HttpStatus> loginPage() {
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping("/systemadmin/")
    public ResponseEntity<SystemAdmin> login(@RequestBody SystemAdmin systemAdmin) {
        logger.info("Try to logging in");
        if (systemAdminService.findSysAdmin(systemAdmin)) {
            logger.info("Login successful");
            return new ResponseEntity<>(systemAdminService.findByUserName(systemAdmin.getUsername()), HttpStatus.OK);
        }
        logger.warn("Login FAILED!");
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
