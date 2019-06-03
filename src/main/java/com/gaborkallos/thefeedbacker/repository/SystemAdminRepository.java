package com.gaborkallos.thefeedbacker.repository;

import com.gaborkallos.thefeedbacker.model.SystemAdmin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SystemAdminRepository extends JpaRepository<SystemAdmin, Long> {
}
