package com.gaborkallos.thefeedbacker.service;

import com.gaborkallos.thefeedbacker.model.SystemAdmin;
import com.gaborkallos.thefeedbacker.repository.SystemAdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class SystemAdminService {

    private PasswordEncoder passwordEncoder;

    private SystemAdminRepository systemAdminRepository;

    @Autowired
    public void setPasswordEncoder() {
        this.passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Autowired
    public void setSystemAdminRepository(SystemAdminRepository systemAdminRepository) {
        this.systemAdminRepository = systemAdminRepository;
    }

    public void setAdministrators() {
        SystemAdmin admin1 = SystemAdmin.builder()
                .username("Asdmann")
                .password(passwordEncoder.encode("12345"))
                .build();
        systemAdminRepository.save(admin1);
        SystemAdmin admin2 = SystemAdmin.builder()
                .username("Kraz")
                .password(passwordEncoder.encode("12345"))
                .build();
        systemAdminRepository.save(admin2);
        System.out.println(admin1.getPassword());
        System.out.println(admin2.getPassword());
    }

    public boolean findSysAdmin(SystemAdmin systemAdmin) {
        List<SystemAdmin> sysAdmins = findAll();
        for(SystemAdmin sysAdmin : sysAdmins){
            if (sysAdmin.getUsername().equals(systemAdmin.getUsername())){
                if(passwordEncoder.matches(systemAdmin.getPassword(),
                        sysAdmin.getPassword())){
                    return true;
                }
                return false;
            }
        }
        return false;
    }

    public List<SystemAdmin> findAll() {
        return systemAdminRepository.findAll();
    }

    public SystemAdmin findByUserName(String name){
        List<SystemAdmin> sysAdmins = findAll();
        for(SystemAdmin sysAdmin : sysAdmins){
            if(sysAdmin.getUsername().equals(name)){
                return sysAdmin;
            }
        }
        return null;
    }
}
