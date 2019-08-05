package com.gaborkallos.thefeedbacker.service;

import com.gaborkallos.thefeedbacker.model.Country;
import com.gaborkallos.thefeedbacker.repository.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CountryService {

    private CountryRepository countryRepository;

    @Autowired
    public void setCountryRepository(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    public boolean addNewCountry(Country newCountry) {
        String name = newCountry.getName();
        newCountry.setName(name.toUpperCase());
        if (findAllCountries().contains(newCountry)) {
            return false;
        }
        countryRepository.save(newCountry);
        return true;
    }

    public List<Country> findAllCountries() {
        return countryRepository.findAll();
    }

    public Country findCountryByName(Country newCountry) {
        for (Country country : countryRepository.findAll()) {
            if (country.getName().toUpperCase().equals(newCountry.getName().toUpperCase())) {
                return country;
            }
        }
        return null;
    }

    public void initCounties() {
        if (findAllCountries().size() == 0) {
            addNewCountry(new Country(1L, "Hungary"));
            addNewCountry(new Country(2L, "United Kingdom"));
            addNewCountry(new Country(3L, "United States"));
        }
    }
}
