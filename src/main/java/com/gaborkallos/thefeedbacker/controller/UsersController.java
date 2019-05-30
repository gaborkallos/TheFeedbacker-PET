package com.gaborkallos.thefeedbacker.controller;

import com.gaborkallos.thefeedbacker.model.Shop;
import com.gaborkallos.thefeedbacker.model.Users;
import com.gaborkallos.thefeedbacker.repository.UsersRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UsersController {

    private static final Logger logger = LoggerFactory.getLogger(UsersController.class);

    private UsersRepository usersRepository;

    @Autowired
    public void setUserRepository(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @GetMapping("/")
    public ResponseEntity<List<Shop>> getUsers() {
        logger.info("Fetching all users...");
        List<Users> users = usersRepository.findAll();
        if (users.isEmpty()) {
            logger.warn("No users available");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        logger.info("Fetch is SUCCESSFUL.");
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
