package com.gaborkallos.thefeedbacker.service;

import com.gaborkallos.thefeedbacker.model.SystemAdmin;
import com.gaborkallos.thefeedbacker.repository.SystemAdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SystemAdminService {

    private SystemAdminRepository systemAdminRepository;

    @Autowired
    public void setSystemAdminRepository(SystemAdminRepository systemAdminRepository) {
        this.systemAdminRepository = systemAdminRepository;
    }

    public void setAdministrators(){
        SystemAdmin admin1 = SystemAdmin.builder()
                .name("Kolloros Akos")
                .username("Asdmann")
                .password("12345")
                .build();
        systemAdminRepository.save(admin1);
        SystemAdmin admin2 = SystemAdmin.builder()
                .name("Kallos Gabor")
                .username("Kraz")
                .password("12345")
                .build();
        systemAdminRepository.save(admin2);

    }

}
