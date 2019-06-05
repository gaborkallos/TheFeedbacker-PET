package com.gaborkallos.thefeedbacker.controller;

import com.gaborkallos.thefeedbacker.model.Feedback;
import com.gaborkallos.thefeedbacker.model.Shop;
import com.gaborkallos.thefeedbacker.repository.ShopRepository;
import com.gaborkallos.thefeedbacker.service.FeedbackService;
import com.gaborkallos.thefeedbacker.service.ShopService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ShopController {

    private static final Logger logger = LoggerFactory.getLogger(ShopController.class);

    private ShopService shopService;
    private ShopRepository shopRepository;
    private FeedbackService feedbackService;

    @Autowired
    public void setFeedbackService(FeedbackService feedbackService) {
        this.feedbackService = feedbackService;
    }

    @Autowired

    public void setShopRepository(ShopRepository shopRepository) {
        this.shopRepository = shopRepository;
    }

    @Autowired
    public void setShopService(ShopService shopService) {
        this.shopService = shopService;
    }

    @GetMapping("/")
    public ResponseEntity<List<Feedback>> myShopFeedbacks(@RequestBody Shop myShop) {
        logger.info("GetMapping at '/' ");
        List<Feedback> myFeedbacks = shopService.myFeedbacks(myShop);
        logger.info(myFeedbacks.toString());
        return new ResponseEntity<List<Feedback>>( HttpStatus.OK);
    }


}
