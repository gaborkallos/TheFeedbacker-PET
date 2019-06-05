package com.gaborkallos.thefeedbacker.service;

import com.gaborkallos.thefeedbacker.model.Feedback;
import com.gaborkallos.thefeedbacker.model.Shop;
import com.gaborkallos.thefeedbacker.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ShopService {

    private UsersRepository usersRepository;
    private ShopRepository shopRepository;
    private CityRepository cityRepository;
    private CountryRepository countryRepository;
    private ShopAdminRepository shopAdminRepository;
    private FeedbackRepository feedbackRepository;

    @Autowired
    public void setFeedbackRepository(FeedbackRepository feedbackRepository) {
        this.feedbackRepository = feedbackRepository;
    }

    @Autowired
    public void setShopAdminRepository(ShopAdminRepository shopAdminRepository) {
        this.shopAdminRepository = shopAdminRepository;
    }

    @Autowired
    public void setCountryRepository(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    @Autowired
    public void setCityRepository(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    @Autowired
    public void setShopRepository(ShopRepository shopRepository) {
        this.shopRepository = shopRepository;
    }

    @Autowired
    public void setUserRepository(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    public List<Feedback> myFeedbacks (Shop myShop){
//        List<Feedback> allFeedbacks = feedbackRepository.findAll();
//        for(Feedback feedback : allFeedbacks){
//            if (feedback.getShop().equals(myShop)){
//                feedbacks.add(feedback);
//            }
//        }
        List<Feedback> feedbacks = feedbackRepository.findFeedbackByShop(myShop);
        return feedbacks;
    }
}


