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
    private PasswordEncoder passwordEncoder;
    private AdminRepository adminRepository;
    private ShopRepository shopRepository;
    private CityRepository cityRepository;
    private CountryRepository countryRepository;


    @Autowired
    public void setCityRepository(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    @Autowired
    public void setCountryRepository(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    @Autowired
    public void setShopRepository(ShopRepository shopRepository) {
        this.shopRepository = shopRepository;
    }


    @Autowired
    public void setPasswordEncoder() {
        this.passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Autowired
    public void setAdminRepository(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    public void setAdministrators() {
        Admin admin1 = Admin.builder()
                .username("Asdmann")
                .password(passwordEncoder.encode("12345"))
                .email("kollorosa@gmail.com")
                .accessRole("systemAdministrator")
                .systemAdmin(true)
                .build();
        adminRepository.save(admin1);
        Admin admin2 = Admin.builder()
                .username("Kraz")
                .password(passwordEncoder.encode("12345"))
                .email("gaborkallos@gmail.com")
                .accessRole("systemAdministrator")
                .systemAdmin(true)
                .build();
        adminRepository.save(admin2);
    }

    public boolean findAdmin(Admin admin) {
        List<Admin> sysAdmins = findAll();
        for (Admin sysAdmin : sysAdmins) {
            if (sysAdmin.getUsername().equals(admin.getUsername())) {
                if (passwordEncoder.matches(admin.getPassword(),
                        sysAdmin.getPassword())) {
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
        return passwordEncoder.encode(plainPassword);
    }

    public List<City> findAllCities() {
        return cityRepository.findAll();
    }

    public boolean addNewCity(City newCity) {
        if (findAllCities().contains(newCity)) {
            return false;
        }
        cityRepository.save(newCity);
        return true;
    }

    public List<Country> findAllCountries() {
        return countryRepository.findAll();
    }

    public boolean addNewCountry(Country newCountry) {
        if (findAllCountries().contains(newCountry)) {
            return false;
        }
        countryRepository.save(newCountry);
        return true;
    }

    public List<Shop> findAllShops() {
        return shopRepository.findAll();
    }

    public boolean addNewShop(Shop newShop) {
        if (findAllShops().contains(newShop)) {
            return false;
        }
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
        String encodedPassword = passwordEncoder.encode(password);
        newAdmin.setPassword(encodedPassword);
        adminRepository.save(newAdmin);
        emailService.sendRegistrationMessage(newAdmin, password);
        return true;
    }

    public boolean isAdminExist(Admin newAdmin) {
        for (Admin admin : findAllAdmin()) {
            if (admin.getEmail().equals(newAdmin.getEmail())) {
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
                currentShop.getAdmins().add(admin);
                shopRepository.save(currentShop);
                return true;
            }
        }
        return false;
    }

}
