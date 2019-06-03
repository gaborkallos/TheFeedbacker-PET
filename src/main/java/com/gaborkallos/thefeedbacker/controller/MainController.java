package com.gaborkallos.thefeedbacker.controller;

import com.gaborkallos.thefeedbacker.model.SystemAdmin;
import com.gaborkallos.thefeedbacker.repository.SystemAdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MainController {

    SystemAdminRepository systemAdminRepository;

    @Autowired
    public void setSystemAdminRepository(SystemAdminRepository systemAdminRepository) {
        this.systemAdminRepository = systemAdminRepository;
    }

    @GetMapping("/systemadmin/getall")
    public ResponseEntity<List<SystemAdmin>> getAllSysAdmin() {
        List<SystemAdmin> systemAdmins = systemAdminRepository.findAll();
        return new ResponseEntity<>(systemAdmins, HttpStatus.OK);
    }

}
