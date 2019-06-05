package com.gaborkallos.thefeedbacker.controller;

import com.gaborkallos.thefeedbacker.model.*;
import com.gaborkallos.thefeedbacker.service.SystemAdminService;
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

    SystemAdminService systemAdminService;

    @Autowired
    public void setSystemAdminService(SystemAdminService systemAdminService) {
        this.systemAdminService = systemAdminService;
    }

    @GetMapping("/systemadmin")
    public ResponseEntity<HttpStatus> loginPage() {
        logger.info("GET on 5000");
        return new ResponseEntity(HttpStatus.OK);
    }


    @PostMapping("/systemadmin")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public ResponseEntity<Boolean> login(@RequestBody SystemAdmin systemAdmin) {
        logger.info("Try to logging in");
        if (systemAdminService.findSysAdmin(systemAdmin)) {
            logger.info("Login successful");
            return new ResponseEntity<>(true, HttpStatus.OK);
        }
        logger.warn("Login FAILED!");
        return new ResponseEntity<>(false, HttpStatus.NOT_FOUND);
    }

    @GetMapping("/city")
    public ResponseEntity<List<City>> getCities() {
        return new ResponseEntity<>(systemAdminService.findAllCities(), HttpStatus.OK);
    }

    @PostMapping("/city")
    public ResponseEntity<List<City>> addCity(@RequestBody City newCity) {
        logger.info("Add new city");
        if (systemAdminService.addNewCity(newCity)) {
            return new ResponseEntity<>(systemAdminService.findAllCities(), HttpStatus.OK);
        }
        logger.info(newCity + "is already exist!");
        return new ResponseEntity<>(systemAdminService.findAllCities(), HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/country")
    public ResponseEntity<List<Country>> getCountries() {
        return new ResponseEntity<>(systemAdminService.findAllCountries(), HttpStatus.OK);
    }

    @PostMapping("/country")
    public ResponseEntity<List<Country>> addCountry(@RequestBody Country newCountry) {
        logger.info("Add new country");
        if (systemAdminService.addNewCountry(newCountry)) {
            return new ResponseEntity<>(systemAdminService.findAllCountries(), HttpStatus.OK);
        }
        logger.info(newCountry + " is already exist!");
        return new ResponseEntity<>(systemAdminService.findAllCountries(), HttpStatus.BAD_REQUEST);

    }

    @PostMapping("/shops")
    public ResponseEntity<Shop> addNewShop(@RequestBody Shop newShop) {
        logger.info("Add new shop");
        systemAdminService.addNewShop(newShop);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/shops")
    public ResponseEntity<Shop> addAdminToShop(@RequestBody Shop shop, ShopAdmin admin) {
        logger.error("Add new Admin to the Shop");
        if (systemAdminService.addAdminToShop(shop, admin)){
            return new ResponseEntity<>(HttpStatus.OK);
        }
        logger.error("Something went wrong!");
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/shopAdmin")
    public ResponseEntity<ShopAdmin> addNewShopAdmin(@RequestBody ShopAdmin newAdmin) {
        logger.info("Add new admin");
        if(systemAdminService.addNewShopAdmin(newAdmin)){
            return new ResponseEntity<>(newAdmin, HttpStatus.OK);
        }
        logger.error(newAdmin + " is already exist!");
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }


    @GetMapping("/shopAdmin")
    public ResponseEntity<List<ShopAdmin>> getShopAdmins() {
        logger.info("Get admins");
        return new ResponseEntity<>(systemAdminService.findAllAdmin(), HttpStatus.OK);
    }
}
