package com.gaborkallos.thefeedbacker.controller;

import com.gaborkallos.thefeedbacker.model.Admin;
import com.gaborkallos.thefeedbacker.model.City;
import com.gaborkallos.thefeedbacker.model.Country;
import com.gaborkallos.thefeedbacker.model.Shop;
import com.gaborkallos.thefeedbacker.service.AdminService;
import com.gaborkallos.thefeedbacker.service.CityService;
import com.gaborkallos.thefeedbacker.service.CountryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class SysAdminController {

    private static final Logger logger = LoggerFactory.getLogger(ShopController.class);

    private AdminService adminService;
    private CityService cityService;
    private CountryService countryService;

    @Autowired
    public void setCountryService(CountryService countryService) {
        this.countryService = countryService;
    }

    @Autowired
    public void setCityService(CityService cityService) {
        this.cityService = cityService;
    }

    @Autowired
    public void setAdminService(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping("/systemadmin")
    public ResponseEntity<HttpStatus> loginPage() {
        return new ResponseEntity(adminService.findAllAdmin(), HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<Admin> login(@RequestBody Admin admin) {
        logger.info("Try to logging in");
        if (adminService.findAdmin(admin)) {
            logger.info("Login successful");
            String accesRole = adminService.findAdminAccesRole(admin);
            admin.setAccessRole(accesRole);
            if (admin.getAccessRole().equals("systemAdministrator")) {
                admin.setSystemAdmin(true);
            } else {
                admin.setSystemAdmin(false);
            }
            return new ResponseEntity<>(admin, HttpStatus.OK);
        }
        logger.warn("Login FAILED!");
        return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
    }

    @PostMapping("/systemadmin")
    public ResponseEntity<HttpStatus> addAdmin(@RequestBody Admin admin) {
        logger.info("Try to register");
        if (adminService.addNewShopAdmin(admin)) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    @GetMapping("/city")
    public ResponseEntity<List<City>> getCities() {
        return new ResponseEntity<>(cityService.findAllCities(), HttpStatus.OK);
    }
//
//    @PostMapping("/city")
//    public ResponseEntity<List<City>> addCity(@RequestBody City newCity) {
//        logger.info("Add new city");
//        if (adminService.addNewCity(newCity)) {
//            return new ResponseEntity<>(adminService.findAllCities(), HttpStatus.OK);
//        }
//        logger.info(newCity + "is already exist!");
//        return new ResponseEntity<>(adminService.findAllCities(), HttpStatus.BAD_REQUEST);
//    }

    @GetMapping("/country")
    public ResponseEntity<List<Country>> getCountries() {
        return new ResponseEntity<>(countryService.findAllCountries(), HttpStatus.OK);
    }

//    @PostMapping("/country")
//    public ResponseEntity<List<Country>> addCountry(@RequestBody Country newCountry) {
//        logger.info("Add new country");
//        if (adminService.addNewCountry(newCountry)) {
//            return new ResponseEntity<>(adminService.findAllCountries(), HttpStatus.OK);
//        }
//        logger.info(newCountry + " is already exist!");
//        return new ResponseEntity<>(adminService.findAllCountries(), HttpStatus.BAD_REQUEST);
//
//    }

    @PostMapping("/shops")
    public ResponseEntity<Boolean> addNewShop(@RequestBody Shop newShop, City newCity, Country newCountry, Admin admin) {
        if (adminService.findAdminAccesRole(admin).equals("systemAdministrator")) {
            logger.info("Add new shop");
            if (cityService.addNewCity(newCity)) {
                logger.info(newCity.getName() + "is added to database!");
            }
            if (countryService.addNewCountry(newCountry)) {
                logger.info(newCountry.getName() + "is added to database!");
            }
            if (adminService.addNewShop(newShop, newCity, newCountry)) {
                logger.info(newShop.getName() + "is added to database!");
            }
            ;
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
