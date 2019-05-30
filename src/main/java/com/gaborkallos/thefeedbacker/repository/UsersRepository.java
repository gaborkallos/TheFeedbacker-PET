package com.gaborkallos.thefeedbacker.repository;

import com.gaborkallos.thefeedbacker.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<Users, Long> {
}
