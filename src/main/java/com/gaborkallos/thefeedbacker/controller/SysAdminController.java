package com.gaborkallos.thefeedbacker.controller;

import com.gaborkallos.thefeedbacker.model.*;
import com.gaborkallos.thefeedbacker.service.AdminService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class SysAdminController {

    private static final Logger logger = LoggerFactory.getLogger(ShopController.class);

    AdminService adminService;

    @Autowired
    public void setAdminService(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping("/systemadmin")
    public ResponseEntity<HttpStatus> loginPage() {
        List<Admin> allAdmin = adminService.findAllAdmin();
        return new ResponseEntity(allAdmin, HttpStatus.OK);
    }

    @PostMapping("/systemadmin")
    public ResponseEntity<String> login(@RequestBody Admin admin) {
        logger.info("Try to logging in");
        if (adminService.findAdmin(admin)) {
            logger.info("Login successful");
            String accesRole = adminService.findAdminAccesRole(admin);
            return new ResponseEntity<>(admin.getAccessRole(), HttpStatus.OK);
        }
        logger.warn("Login FAILED!");
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @GetMapping("/city")
    public ResponseEntity<List<City>> getCities() {
        return new ResponseEntity<>(adminService.findAllCities(), HttpStatus.OK);
    }

    @PostMapping("/city")
    public ResponseEntity<List<City>> addCity(@RequestBody City newCity) {
        logger.info("Add new city");
        if (adminService.addNewCity(newCity)) {
            return new ResponseEntity<>(adminService.findAllCities(), HttpStatus.OK);
        }
        logger.info(newCity + "is already exist!");
        return new ResponseEntity<>(adminService.findAllCities(), HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/country")
    public ResponseEntity<List<Country>> getCountries() {
        return new ResponseEntity<>(adminService.findAllCountries(), HttpStatus.OK);
    }

    @PostMapping("/country")
    public ResponseEntity<List<Country>> addCountry(@RequestBody Country newCountry) {
        logger.info("Add new country");
        if (adminService.addNewCountry(newCountry)) {
            return new ResponseEntity<>(adminService.findAllCountries(), HttpStatus.OK);
        }
        logger.info(newCountry + " is already exist!");
        return new ResponseEntity<>(adminService.findAllCountries(), HttpStatus.BAD_REQUEST);

    }

    @PostMapping("/shops")
    public ResponseEntity<Boolean> addNewShop(@RequestBody Shop newShop, Admin admin) {
        if (adminService.findAdminAccesRole(admin).equals("systemAdministrator")) {
            logger.info("Add new shop");
            adminService.addNewShop(newShop);
            return new ResponseEntity<>(true, HttpStatus.OK);
        }
        return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/shops")
    public ResponseEntity<Shop> addAdminToShop(@RequestBody Shop shop, Admin admin) {
        logger.info("Try to add new Admin to the Shop");
        if (adminService.addAdminToShop(shop, admin)) {
            logger.info("Success! New shop is added");
            return new ResponseEntity<>(HttpStatus.OK);
        }
        logger.error("Access denied!");
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/admins")
    public ResponseEntity<List<Admin>> getAllAdmins(@RequestBody Admin sysAdmin) {
        if (adminService.findAdminAccesRole(sysAdmin).equals("systemAdministrator")) {
            List<Admin> allAdmin = adminService.findAllAdmin();
            return new ResponseEntity<>(allAdmin, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }
}
