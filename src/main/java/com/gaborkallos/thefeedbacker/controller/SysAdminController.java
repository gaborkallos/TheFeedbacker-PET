package com.gaborkallos.thefeedbacker.controller;

import com.gaborkallos.thefeedbacker.model.Admin;
import com.gaborkallos.thefeedbacker.model.City;
import com.gaborkallos.thefeedbacker.model.Country;
import com.gaborkallos.thefeedbacker.model.Shop;
import com.gaborkallos.thefeedbacker.service.AdminService;
import com.gaborkallos.thefeedbacker.service.CityService;
import com.gaborkallos.thefeedbacker.service.CountryService;
import com.gaborkallos.thefeedbacker.service.ShopService;
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

    @Autowired
    private AdminService adminService;
    @Autowired
    private CityService cityService;
    @Autowired
    private CountryService countryService;
    @Autowired
    private ShopService shopService;


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
        return new ResponseEntity<>((Admin) null, HttpStatus.UNAUTHORIZED);
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

    @GetMapping("/country")
    public ResponseEntity<List<Country>> getCountries() {
        return new ResponseEntity<>(countryService.findAllCountries(), HttpStatus.OK);
    }


    //TODO: https://codecool.gitlab.io/codecool-curriculum/bud-advanced-java-new/#/.
    // ./pages/java/spring/spring-security-tutorial !!!

    @PostMapping("/shops")
    //TODO:    @Secured({"ROLE_systemAdministrator"})
    public ResponseEntity<Boolean> addNewShop(@RequestBody Shop newShop) {
        logger.info("Add new shop");
        if (cityService.addNewCity(newShop.getCity())) {
            logger.info(newShop.getCity().getName() + " is added to database!");
        }
        if (countryService.addNewCountry(newShop.getCountry())) {
            logger.info(newShop.getCountry().getName() + " is added to database!");
        }
        if (adminService.addNewShop(newShop, newShop.getCity(), newShop.getCountry())) {
            logger.info(newShop.getName() + " is added to database!");
        }
        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    @GetMapping("/shops")
    public ResponseEntity<List<Shop>> getAllShops() {
//        if (admin.isSystemAdmin()) {
            return new ResponseEntity<>(shopService.findAllShop(), HttpStatus.OK);
//        }
//        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @PutMapping("/shops")
    public ResponseEntity<Shop> addAdminToShop(@RequestBody Shop shop, Admin admin) {
        logger.info("Try to add new Admin to the Shop");
        if (adminService.addAdminToShop(shop, admin)) {
            logger.info("Success! Admin: " + shop.getAdmins().toString() + " added to shop "+shop.getName() + "!");
            return new ResponseEntity<>(HttpStatus.OK);
        }
        logger.error("Access denied!");
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/admins")
    public ResponseEntity<List<Admin>> getAllAdmins() {
//        if (adminService.findAdminAccesRole(sysAdmin).equals("systemAdministrator")) {
            List<Admin> allAdmin = adminService.findAllAdmin();
            return new ResponseEntity<>(allAdmin, HttpStatus.OK);
//        }
//        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }
}
