package com.gaborkallos.thefeedbacker.repository;

import com.gaborkallos.thefeedbacker.model.Country;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CountryRepository extends JpaRepository<Country, Long> {
}
