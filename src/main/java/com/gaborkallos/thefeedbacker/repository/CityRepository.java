package com.gaborkallos.thefeedbacker.repository;

import com.gaborkallos.thefeedbacker.model.City;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CityRepository extends JpaRepository<City, Long> {
}
