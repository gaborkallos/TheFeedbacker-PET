package com.gaborkallos.thefeedbacker.repository;

import com.gaborkallos.thefeedbacker.model.Shop;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;


public interface ShopRepository extends JpaRepository<Shop, Long> {

}
