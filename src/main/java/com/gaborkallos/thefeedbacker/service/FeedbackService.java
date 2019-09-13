package com.gaborkallos.thefeedbacker.service;

import com.gaborkallos.thefeedbacker.model.Admin;
import com.gaborkallos.thefeedbacker.model.Feedback;
import com.gaborkallos.thefeedbacker.model.Shop;
import com.gaborkallos.thefeedbacker.repository.FeedbackRepository;
import com.gaborkallos.thefeedbacker.repository.InvoiceRepository;
import com.gaborkallos.thefeedbacker.repository.ShopRepository;
import com.gaborkallos.thefeedbacker.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeedbackService {

    private FeedbackRepository feedbackRepository;
    private UsersRepository usersRepository;
    private InvoiceRepository invoiceRepository;
    private AdminService adminService;
    private ShopService shopService;

    @Autowired
    public void setShopService(ShopService shopService) {
        this.shopService = shopService;
    }

    @Autowired
    public void setAdminService(AdminService adminService) {
        this.adminService = adminService;
    }

    @Autowired
    public void setInvoiceRepository(InvoiceRepository invoiceRepository) {
        this.invoiceRepository = invoiceRepository;
    }

    @Autowired
    public void setUsersRepository(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }


    @Autowired
    public void setFeedbackRepository(FeedbackRepository feedbackRepository) {
        this.feedbackRepository = feedbackRepository;
    }

    public boolean addFeedback(Admin admin, Feedback feedback){
//        usersRepository.save(feedback.getUser());
//        invoiceRepository.save(feedback.getInvoice());
        List<Shop> myShops = shopService.findShopByAdmin(admin);
        if(myShops.size() > 0){
            feedback.setShop(myShops.get(0));
            System.out.println(feedback.toString());
//            feedbackRepository.save(feedback);
            return true;
        }
        return false;
    }

}
