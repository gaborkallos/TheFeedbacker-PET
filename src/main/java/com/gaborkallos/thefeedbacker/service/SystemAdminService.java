package com.gaborkallos.thefeedbacker.service;

import com.gaborkallos.thefeedbacker.model.*;
import com.gaborkallos.thefeedbacker.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SystemAdminService {


    private RandomGenerator randomGenerator;
    private EmailService emailService;
    private PasswordEncoder passwordEncoder;
    private SystemAdminRepository systemAdminRepository;
    private ShopAdminRepository shopAdminRepository;
    private ShopRepository shopRepository;
    private CityRepository cityRepository;
    private CountryRepository countryRepository;

    @Autowired
    public void setShopAdminRepository(ShopAdminRepository shopAdminRepository) {
        this.shopAdminRepository = shopAdminRepository;
    }

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
    public void setSystemAdminRepository(SystemAdminRepository systemAdminRepository) {
        this.systemAdminRepository = systemAdminRepository;
    }

    public void setAdministrators() {
        SystemAdmin admin1 = SystemAdmin.builder()
                .username("Asdmann")
                .password(passwordEncoder.encode("12345"))
                .isSystemAdmin(true)
                .build();
        systemAdminRepository.save(admin1);
        SystemAdmin admin2 = SystemAdmin.builder()
                .username("Kraz")
                .password(passwordEncoder.encode("12345"))
                .isSystemAdmin(true)
                .build();
        systemAdminRepository.save(admin2);
    }

    public boolean findSysAdmin(SystemAdmin systemAdmin) {
        List<SystemAdmin> sysAdmins = findAll();
        for (SystemAdmin sysAdmin : sysAdmins) {
            if (sysAdmin.getUsername().equals(systemAdmin.getUsername())) {
                if (passwordEncoder.matches(systemAdmin.getPassword(),
                        sysAdmin.getPassword())) {
                    return true;
                }
                return false;
            }
        }
        return false;
    }

    public List<SystemAdmin> findAll() {
        return systemAdminRepository.findAll();
    }

    public SystemAdmin findByUserName(String name) {
        List<SystemAdmin> sysAdmins = findAll();
        for (SystemAdmin sysAdmin : sysAdmins) {
            if (sysAdmin.getUsername().equals(name)) {
                return sysAdmin;
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

    public boolean addNewShopAdmin(ShopAdmin newAdmin) {
        if (findAllAdmin().contains(newAdmin)) {
            return false;
        }
        String password = (randomGenerator.passwordGenerator());
        String encodedPassword = passwordEncoder.encode(password);
        newAdmin.setPassword(encodedPassword);
        shopAdminRepository.save(newAdmin);
        //TODO: send email to customer with the password!!!
        emailService.sendRegistrationMessage(newAdmin, password);
        return true;
    }

    public List<ShopAdmin> findAllAdmin() {
        return shopAdminRepository.findAll();
    }

    public boolean addAdminToShop(Shop shop, ShopAdmin admin) {
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
