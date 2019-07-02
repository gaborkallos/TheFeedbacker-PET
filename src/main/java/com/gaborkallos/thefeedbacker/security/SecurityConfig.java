package com.gaborkallos.thefeedbacker.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/auth/**").permitAll() // allowed by anyone
                .antMatchers(HttpMethod.POST, "/systemadmin").hasRole("systemAdministrator") // allowed only when signed in
                .antMatchers(HttpMethod.GET, "/systemadmin").hasRole("systemAdministrator") // allowed only when signed in
                .antMatchers(HttpMethod.GET, "/shops").hasRole("systemAdministrator") // allowed only when signed in
                .antMatchers(HttpMethod.GET, "/admins").hasRole("systemAdministrator") // allowed only when signed in
                .antMatchers(HttpMethod.DELETE, "/**").hasRole("systemAdministrator") // allowed if signed in with ADMIN role
                .antMatchers(HttpMethod.PUT, "/**").hasRole("systemAdministrator") // allowed if signed in with ADMIN role
                .antMatchers(HttpMethod.POST, "/shop/**").authenticated();// allowed only when signed in
                //.anyRequest().denyAll(); // anything else is denied
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public BCryptPasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }
}
