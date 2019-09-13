package com.gaborkallos.thefeedbacker.controller;

import com.gaborkallos.thefeedbacker.model.*;
import com.gaborkallos.thefeedbacker.repository.ShopRepository;
import com.gaborkallos.thefeedbacker.security.JwtTokenServices;
import com.gaborkallos.thefeedbacker.service.AdminService;
import com.gaborkallos.thefeedbacker.service.FeedbackService;
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
public class ShopController {

    private static final Logger logger = LoggerFactory.getLogger(ShopController.class);

    private ShopService shopService;
    private AdminService adminService;
    private FeedbackService feedbackService;
    private JwtTokenServices jwtTokenServices;

    @Autowired
    public void setJwtTokenServices(JwtTokenServices jwtTokenServices) {
        this.jwtTokenServices = jwtTokenServices;
    }

    @Autowired
    public void setAdminService(AdminService adminService) {
        this.adminService = adminService;
    }

    @Autowired
    public void setFeedbackService(FeedbackService feedbackService) {
        this.feedbackService = feedbackService;
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

    @PostMapping("/feedback")
    public ResponseEntity<HttpStatus> leaveFeedback(@RequestHeader String Authorization, @RequestBody Feedback newfeedback) {
        String adminName = jwtTokenServices.getUserName(Authorization);
        Admin myAdmin = adminService.findByUserName(adminName);
        System.out.println(newfeedback.getInvoice().toString());
//        System.out.println(newfeedback.getShop().toString());
        System.out.println(newfeedback.getUser().toString());
        System.out.println(newfeedback.toString());
//        if (feedbackService.addFeedback(myAdmin, newfeedback)) {
//            return new ResponseEntity<>(HttpStatus.OK);
//
//        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/myshops")
    public List<Shop> getMyShops(@RequestHeader String Authorization){
        String adminName = jwtTokenServices.getUserName(Authorization);
        System.out.println(adminName);
        Admin myAdmin = adminService.findByUserName(adminName);
        System.out.println(myAdmin.toString());
        List<Shop> myShops = shopService.findShopByAdmin(myAdmin);
        return myShops;
    }
}
