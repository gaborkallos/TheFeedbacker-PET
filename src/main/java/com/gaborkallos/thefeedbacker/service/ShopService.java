package com.gaborkallos.thefeedbacker.service;

import com.gaborkallos.thefeedbacker.model.Admin;
import com.gaborkallos.thefeedbacker.model.Feedback;
import com.gaborkallos.thefeedbacker.model.Shop;
import com.gaborkallos.thefeedbacker.model.Users;
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
    private FeedbackRepository feedbackRepository;

    @Autowired
    public void setFeedbackRepository(FeedbackRepository feedbackRepository) {
        this.feedbackRepository = feedbackRepository;
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


    public List<Feedback> myFeedbacks(Shop myShop, Admin admin) {
        if (admin.getAccessRole().equals("shopAdministrator") ||
                admin.getAccessRole().equals("systemAdministrator")) {
            List<Feedback> feedbacks = feedbackRepository.findFeedbackByShop(myShop);
            return feedbacks;
        }
        return null;
    }

    public boolean isMyShop(Admin admin, Shop myShop) {
        for (Shop currentShop : shopRepository.findAll()) {
            if (currentShop.getName().equals(myShop.getName())) {
                List<Admin> myAdminsmyShop = currentShop.getAdmins();
                for (Admin currrentAdmin : myAdminsmyShop) {
                    if (currrentAdmin.getUsername().equals(admin.getUsername())) {
                        return true;
                    }
                }

            }
        }
        return false;
    }

    public List<Shop> findShopByAdmin(Admin admin) {
        List<Shop> result = new ArrayList<>();
        for (Shop shop : shopRepository.findAll()) {
            System.out.println(shop);
            if (shop.getAdmins().contains(admin)) {
                System.out.println("------ Your Shops ------");
                System.out.println("- " + shop.toString());
                result.add(shop);
            }
        }
        return result;
    }

    public Shop findShopByName(Shop newShop) {
        for (Shop shop : shopRepository.findAll()) {
            if (shop.getName().toUpperCase().equals(newShop.getName().toUpperCase())) {
                return newShop;
            }
        }
        return null;
    }

    public boolean isShopExist(Shop newShop){
        for (Shop shop : shopRepository.findAll()) {
            if (shop.getName().toUpperCase().equals(newShop.getName().toUpperCase())) {
                return true;
            }
        }
        return false;
    }

    public List<Shop> findAllShop() {
        return shopRepository.findAll();
    }

    public void leaveFeedback(Feedback feedback) {
        feedbackRepository.save(feedback);
    }

    public void saveUser(Users user) {
        usersRepository.save(user);
    }

    public List<Shop> findAll() {
        return shopRepository.findAll();
    }

    public void save(Shop newShop) {
        shopRepository.save(newShop);
    }
}


