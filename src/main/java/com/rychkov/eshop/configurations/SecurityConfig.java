package com.rychkov.eshop.configurations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
            .antMatchers("/", "/**").permitAll()
            .antMatchers("/adminpage", "/adminpage**")
            .hasAnyRole("Admin", "Manager")
            .antMatchers("/login", "/registration").anonymous()
            .and()
            .formLogin()
            .loginPage("/login")
            .failureUrl("/login.html?error=true")
            .defaultSuccessUrl("/homepage")
            .and()
            .logout()
            .logoutSuccessUrl("/");


    }

}



