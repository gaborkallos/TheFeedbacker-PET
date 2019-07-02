package com.gaborkallos.thefeedbacker.security;


import com.gaborkallos.thefeedbacker.model.Admin;
import com.gaborkallos.thefeedbacker.repository.AdminRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CustomUserDetailsService implements UserDetailsService {

    private AdminRepository admins;

    public CustomUserDetailsService(AdminRepository admins) {
        this.admins = admins;
    }

    /**
     * Loads the user from the DB and converts it to Spring Security's internal User object
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Admin admin = admins.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username: " + username + " not found"));
        List<String> accessRole = new ArrayList<>();
        accessRole.add(admin.getAccessRole());
        return new User(admin.getUsername(), admin.getPassword(),
                accessRole.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()));
    }
}
