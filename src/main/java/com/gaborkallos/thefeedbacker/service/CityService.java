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

    public void addNewCity(City newCity) {
        String name = newCity.getName();
        newCity.setName(name.toUpperCase());
        if (!isCityExist(newCity)) {
            cityRepository.save(newCity);
        }
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

    private boolean isCityExist(City newCity){
        for (City city : cityRepository.findAll()) {
            if (city.getName().toUpperCase().equals(newCity.getName().toUpperCase())) {
                return true;
            }
        }
        return false;
    }

    public void initCities() {
        if (findAllCities().size() == 0) {
            addNewCity(new City(1L, "Budapest"));
            addNewCity(new City(2L, "London"));
            addNewCity(new City(3L, "New York"));
        }
    }
}
