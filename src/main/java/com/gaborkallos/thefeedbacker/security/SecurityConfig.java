package com.gaborkallos.thefeedbacker.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtTokenServices jwtTokenServices;

    public SecurityConfig(JwtTokenServices jwtTokenServices) {
        this.jwtTokenServices = jwtTokenServices;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable()
                .csrf().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/auth/**").permitAll() // allowed by anyone
                .antMatchers(HttpMethod.POST, "/systemadmin").hasRole("systemAdministrator") // allowed if signed in with SYSADMIN role
                .antMatchers(HttpMethod.GET, "/systemadmin").hasRole("systemAdministrator") // allowed if signed in with SYSADMIN role
                .antMatchers(HttpMethod.GET, "/shops").hasRole("systemAdministrator") // allowed if signed in with SYSADMIN role
                .antMatchers(HttpMethod.PUT, "/shops").hasRole("systemAdministrator") // allowed if signed in with SYSADMIN role
                .antMatchers(HttpMethod.GET, "/admins").hasRole("systemAdministrator") // allowed if signed in with SYSADMIN role
                .antMatchers(HttpMethod.DELETE, "/**").hasRole("systemAdministrator") // allowed if signed in with SYSADMIN role
                .antMatchers(HttpMethod.PUT, "/**").hasRole("systemAdministrator") // allowed if signed in with SYSADMIN role
                .antMatchers(HttpMethod.POST, "/shop/**").authenticated()// allowed only when signed in
                //.anyRequest().denyAll(); // anything else is denied
                .and()
                .addFilterBefore(new JwtTokenFilter(jwtTokenServices), UsernamePasswordAuthenticationFilter.class);
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
