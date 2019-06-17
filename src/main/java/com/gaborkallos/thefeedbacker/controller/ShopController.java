package com.gaborkallos.thefeedbacker.controller;

import com.gaborkallos.thefeedbacker.model.Admin;
import com.gaborkallos.thefeedbacker.model.Feedback;
import com.gaborkallos.thefeedbacker.model.Shop;
import com.gaborkallos.thefeedbacker.model.Users;
import com.gaborkallos.thefeedbacker.repository.ShopRepository;
import com.gaborkallos.thefeedbacker.service.AdminService;
import com.gaborkallos.thefeedbacker.service.FeedbackService;
import com.gaborkallos.thefeedbacker.service.ShopService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ShopController {

    private static final Logger logger = LoggerFactory.getLogger(ShopController.class);

    private ShopService shopService;
    private AdminService adminService;
    private ShopRepository shopRepository;
    private FeedbackService feedbackService;

    @Autowired
    public void setAdminService(AdminService adminService) {
        this.adminService = adminService;
    }

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

    @GetMapping("/feedback")
    public ResponseEntity<List<Feedback>> myShopFeedbacks(@RequestBody Shop myShop, Admin admin) {
        logger.info("Get all feedbacks for " + myShop.getName());
        List<Feedback> myFeedbacks = shopService.myFeedbacks(myShop, admin);
        logger.info(myFeedbacks.toString());
        return new ResponseEntity<>(myFeedbacks, HttpStatus.OK);

    }

    @PutMapping("/feedback")
    public ResponseEntity<HttpStatus> leaveFeedback(@RequestBody Shop myShop, Admin admin, Feedback feedback, Users user){
        logger.info("Try to leave feedback for" + myShop.getName());
        if(shopService.isMyShop(admin,myShop)){
            shopService.leaveFeedback(feedback);
            shopService.saveUser(user);
            logger.info("Success to leave a feedback!");


            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }
}
