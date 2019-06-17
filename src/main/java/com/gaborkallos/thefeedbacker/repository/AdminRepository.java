package com.gaborkallos.thefeedbacker.repository;

import com.gaborkallos.thefeedbacker.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin, Long> {

}
