package com.gaborkallos.thefeedbacker.service;

import com.gaborkallos.thefeedbacker.model.City;
import com.gaborkallos.thefeedbacker.model.Country;
import com.gaborkallos.thefeedbacker.model.Shop;
import com.gaborkallos.thefeedbacker.model.Admin;
import com.gaborkallos.thefeedbacker.repository.CityRepository;
import com.gaborkallos.thefeedbacker.repository.CountryRepository;
import com.gaborkallos.thefeedbacker.repository.ShopRepository;
import com.gaborkallos.thefeedbacker.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {


    @Autowired
    private RandomGenerator randomGenerator;
    @Autowired
    private EmailService emailService;
    private AdminRepository adminRepository;
    private ShopRepository shopRepository;
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
    public void setShopRepository(ShopRepository shopRepository) {
        this.shopRepository = shopRepository;
    }


    @Autowired
    private BCryptPasswordEncoder encoder;


    @Autowired
    public void setAdminRepository(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    public void setAdministrators() {
        if (!isSysadminExist("Kraz")) {
            Admin admin1 = Admin.builder()
                    .username("Asdmann")
                    .password(encoder.encode("12345"))
                    .email("kollorosa@gmail.com")
                    .accessRole("systemAdministrator")
                    .systemAdmin(true)
                    .build();
            adminRepository.save(admin1);
            Admin admin2 = Admin.builder()
                    .username("Kraz")
                    .password(encoder.encode("12345"))
                    .email("gaborkallos@gmail.com")
                    .accessRole("systemAdministrator")
                    .systemAdmin(true)
                    .build();
            adminRepository.save(admin2);
        }
    }

    public boolean findAdmin(Admin admin) {
        List<Admin> sysAdmins = findAll();
        for (Admin sysAdmin : sysAdmins) {
            if (sysAdmin.getUsername().equals(admin.getUsername())) {
                if (encoder.matches(admin.getPassword(), sysAdmin.getPassword())) {
                    return true;
                }
            }
        }
        return false;
    }

    public String findAdminAccesRole(Admin admin) {
        for (Admin currentAdmin : findAll()) {
            if (currentAdmin.getUsername().equals(admin.getUsername())) {
                return currentAdmin.getAccessRole();
            }
        }
        return null;
    }

    public List<Admin> findAll() {
        return adminRepository.findAll();
    }

    public Admin findByUserName(String name) {
        List<Admin> admins = findAll();
        for (Admin admin : admins) {
            if (admin.getUsername().equals(name)) {
                return admin;
            }
        }
        return null;
    }

    public String encryptPassword(String plainPassword) {
        return encoder.encode(plainPassword);
    }

    public List<Shop> findAllShops() {
        return shopRepository.findAll();
    }

    public boolean addNewShop(Shop newShop, City newCity, Country newCountry) {
        if (findAllShops().contains(newShop)) {
            return false;
        }
        newShop.setCity(cityService.findCityByName(newCity));
        newShop.setCountry(countryService.findCountryByName(newCountry));
        shopRepository.save(newShop);
        return true;
    }

    public boolean addNewShopAdmin(Admin newAdmin) {
        if (isAdminExist(newAdmin)) {
            return false;
        }

        if (newAdmin.isSystemAdmin()) {
            newAdmin.setAccessRole("systemAdministrator");
        } else {
            newAdmin.setAccessRole("shopAdministrator");
        }
        String password = (randomGenerator.passwordGenerator());
        String encodedPassword = encoder.encode(password);
        newAdmin.setPassword(encodedPassword);
        adminRepository.save(newAdmin);
        emailService.sendRegistrationMessage(newAdmin, password);
        return true;
    }

    private boolean isAdminExist(Admin newAdmin) {
        for (Admin admin : findAllAdmin()) {
            if (admin.getEmail().equals(newAdmin.getEmail())) {
                return true;
            }
        }
        return false;
    }

    private boolean isSysadminExist(String username) {
        for (Admin admin : findAllAdmin()) {
            if (admin.getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }

    public List<Admin> findAllAdmin() {
        return adminRepository.findAll();
    }

    public boolean addAdminToShop(Shop shop, Admin admin) {
        for (Shop currentShop : findAllShops()) {
            if (currentShop.equals(shop)) {
                List<Admin> admins = currentShop.getAdmins();
                admins.add(admin);
                currentShop.setAdmins(admins);
                return true;
            }
        }
        return false;
    }

}
