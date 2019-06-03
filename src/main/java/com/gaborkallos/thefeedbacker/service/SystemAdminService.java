package com.gaborkallos.thefeedbacker.service;

import com.gaborkallos.thefeedbacker.model.SystemAdmin;
import com.gaborkallos.thefeedbacker.repository.SystemAdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class SystemAdminService {

    private SystemAdminRepository systemAdminRepository;

    @Autowired
    public void setSystemAdminRepository(SystemAdminRepository systemAdminRepository) {
        this.systemAdminRepository = systemAdminRepository;
    }

    public void setAdministrators() {
        SystemAdmin admin1 = SystemAdmin.builder()
                .username("Asdmann")
                .password("12345")
                .build();
        systemAdminRepository.save(admin1);
        SystemAdmin admin2 = SystemAdmin.builder()
                .username("Kraz")
                .password("12345")
                .build();
        systemAdminRepository.save(admin2);

    }

    public boolean findSysAdmin(SystemAdmin systemAdmin) {
        List<SystemAdmin> sysAdmins = findAll();
        for(SystemAdmin sysAdmin : sysAdmins){
            if (sysAdmin.getUsername().equals(systemAdmin.getUsername())){
                if(sysAdmin.getPassword().equals(systemAdmin.getPassword())){
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
