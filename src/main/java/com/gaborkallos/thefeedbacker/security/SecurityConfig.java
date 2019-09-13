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
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

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
                .cors().and()
                .csrf().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/auth/**").permitAll() // allowed by anyone
                .antMatchers(HttpMethod.POST, "/systemadmin").hasRole("systemAdministrator") // allowed if signed in with SYSADMIN role
                .antMatchers(HttpMethod.GET, "/systemadmin").hasRole("systemAdministrator") // allowed if signed in with SYSADMIN role
                .antMatchers(HttpMethod.GET, "/admins").hasRole("systemAdministrator") // allowed if signed in with SYSADMIN role
                .antMatchers(HttpMethod.GET, "/city").hasRole("systemAdministrator") // allowed if signed in with SYSADMIN role
                .antMatchers(HttpMethod.GET, "/country").hasRole("systemAdministrator") // allowed if signed in with SYSADMIN role
                .antMatchers("/shops").hasRole("systemAdministrator") // allowed if signed in with SYSADMIN role
                .antMatchers(HttpMethod.GET, "/**").hasRole("systemAdministrator") // allowed if signed in with SYSADMIN role
                .antMatchers(HttpMethod.POST, "/shop/**").authenticated()// allowed only when signed in
                .antMatchers(HttpMethod.POST, "/feedback").hasRole("shopAdministrator")// allowed only when signed in
                .antMatchers(HttpMethod.GET, "/feedback").hasRole("shopAdministrator")// allowed only when signed in
                .antMatchers("/myshops").authenticated()//allowed only when signed in

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

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("authorization", "content-type", "x-auth-token"));
        configuration.setExposedHeaders(Arrays.asList("x-auth-token"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
