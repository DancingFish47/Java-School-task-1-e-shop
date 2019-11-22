package com.rychkov.eshop.services.implementations;

import com.rychkov.eshop.configurations.AppConfiguration;
import com.rychkov.eshop.configurations.PersistenceJPAConfig;
import com.rychkov.eshop.entities.User;
import com.rychkov.eshop.repositories.UserRepository;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppConfiguration.class, PersistenceJPAConfig.class})
@WebAppConfiguration
public class UserDetailsServiceImplTest {
    @Autowired
    UserDetailsService userDetailsService;
    @Autowired
    UserRepository userRepository;

    @Test
    public void loadUserByUsername() {
        User user = User.builder()
                .username("Test")
                .userRole("TestRole")
                .build();

        userRepository.save(user);
        assertEquals("Test", userDetailsService.loadUserByUsername("Test").getUsername());
    }

    @After
    public void clear() {
        userRepository.deleteAll();
    }
}