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
@CrossOrigin
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

    @PostMapping("/shops")
    public ResponseEntity<Boolean> addNewShop(@RequestBody Shop newShop) {
        logger.info("Add new shop");
        cityService.addNewCity(newShop.getCity());
        countryService.addNewCountry(newShop.getCountry());
        if (adminService.addNewShop(newShop, newShop.getCity(), newShop.getCountry())) {
            logger.info(newShop.getName() + " is added to database!");
            return new ResponseEntity<>(true, HttpStatus.OK);
        }
        return new ResponseEntity<>(false, HttpStatus.CONFLICT);
    }

    @GetMapping("/shops")
    public ResponseEntity<List<Shop>> getAllShops() {
        return new ResponseEntity<>(shopService.findAllShop(), HttpStatus.OK);
    }

    @PutMapping("/shops")
    public ResponseEntity<Shop> addAdminToShop(@RequestBody Shop shop) {
        logger.info("Try to add new Admin to the Shop");
        List<Admin> admins = shop.getAdmins();
        if (adminService.addAdminToShop(shop, admins)) {
            logger.info("Success! New shop is added");
            return new ResponseEntity<>(HttpStatus.OK);
        }
        logger.error("Access denied!");
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/admins")
    public ResponseEntity<List<Admin>> getAllAdmins() {
        List<Admin> allAdmin = adminService.findAllAdmin();
        return new ResponseEntity<>(allAdmin, HttpStatus.OK);
    }
}
