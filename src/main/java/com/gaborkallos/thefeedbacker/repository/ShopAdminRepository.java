package com.gaborkallos.thefeedbacker.repository;

import com.gaborkallos.thefeedbacker.model.ShopAdmin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ShopAdminRepository extends JpaRepository<ShopAdmin, Long> {

}
