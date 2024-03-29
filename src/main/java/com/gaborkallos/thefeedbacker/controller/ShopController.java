package com.gaborkallos.thefeedbacker.controller;

import com.gaborkallos.thefeedbacker.model.Shop;
import com.gaborkallos.thefeedbacker.repository.ShopRepository;
import com.gaborkallos.thefeedbacker.service.ShopService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ShopController {

    private static final Logger logger = LoggerFactory.getLogger(ShopController.class);

    private ShopService shopService;
    private ShopRepository shopRepository;

    @Autowired

    public void setShopRepository(ShopRepository shopRepository) {
        this.shopRepository = shopRepository;
    }

    @Autowired
    public void setShopService(ShopService shopService) {
        this.shopService = shopService;
    }

    @GetMapping("/")
    public ResponseEntity<List<Shop>> getUsers() {
        logger.info("GetMapping at '/' ");
        logger.info("Fetch is SUCCESSFUL.");
        List<Shop> shops = shopRepository.findAll();
        return new ResponseEntity<>(shops, HttpStatus.OK);
    }


}
