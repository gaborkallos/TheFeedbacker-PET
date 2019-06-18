package com.gaborkallos.thefeedbacker.service;

import com.gaborkallos.thefeedbacker.model.City;
import com.gaborkallos.thefeedbacker.repository.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CityService {

    private CityRepository cityRepository;

    @Autowired
    public void setCityRepository(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    public boolean addNewCity(City newCity) {
        String name = newCity.getName();
        newCity.setName(name.toUpperCase());
        if (findAllCities().contains(newCity)) {
            return false;
        }
        cityRepository.save(newCity);
        return true;
    }

    public List<City> findAllCities() {
        return cityRepository.findAll();
    }

    public City findCityByName(City newCity) {
        for (City city : cityRepository.findAll()) {
            if (city.getName().toUpperCase().equals(newCity.getName().toUpperCase())) {
                return city;
            }
        }
        return null;
    }

}
