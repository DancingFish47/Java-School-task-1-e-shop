package com.rychkov.eshop.services.implementations;

import com.rychkov.eshop.configurations.AppConfiguration;
import com.rychkov.eshop.configurations.PersistenceJPAConfig;
import com.rychkov.eshop.dtos.AddressDto;
import com.rychkov.eshop.dtos.UserMainInfoDto;
import com.rychkov.eshop.dtos.PasswordDto;
import com.rychkov.eshop.dtos.UserDto;
import com.rychkov.eshop.entities.Address;
import com.rychkov.eshop.entities.User;
import com.rychkov.eshop.repositories.AddressesRepository;
import com.rychkov.eshop.repositories.UserRepository;
import com.rychkov.eshop.services.UserService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppConfiguration.class, PersistenceJPAConfig.class})
@WebAppConfiguration
public class UserServiceImplTest {
    @Autowired
    UserService userService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    AddressesRepository addressesRepository;
    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    private User user;
    private Address address;

    @Before
    public void setUp(){
        user = User.builder()
                .username("User")
                .password(passwordEncoder.encode("1234"))
                .email("Email@mail.ru")
                .build();
        user = userRepository.save(user);

        address = Address.builder()
                .zip("111111")
                .user(user)
                .street("street")
                .building("building")
                .apartment("apartment")
                .city("city")
                .country("country")
                .build();
        address = addressesRepository.save(address);
    }

    @Test
    public void registerNewUser() {
        UserDto userDto = new UserDto();
        userDto.setUsername("Test");
        userDto.setEmail("Test@mail.ru");
        userDto.setPassword("1234");

        userService.registerNewUser(userDto);
        assertEquals("Test" ,userRepository.findByUsername(userDto.getUsername()).getUsername());

    }

    @Test
    public void changeMainSettings() {
        UserMainInfoDto userMainInfoDto = UserMainInfoDto.builder()
                .birthdate("19/06/1996")
                .firstname("Edited")
                .lastname("Edited")
                .build();

        userService.editMainInfo(userMainInfoDto, user.getId());
        assertEquals("Edited", userRepository.findByUsername(user.getUsername()).getFirstName());
    }

    @Test
    public void changePassword() {
        PasswordDto passwordDto = PasswordDto.builder()
                .currentPassword("1234")
                .newPassword("12345")
                .build();
        String currentPassword = userRepository.findByUsername(user.getUsername()).getPassword();
        userService.changePassword(passwordDto, user.getId());
        assertNotEquals(currentPassword, userRepository.findByUsername(user.getUsername()).getPassword());
    }

    @Test
    public void editAddress() {
        AddressDto addressDto = AddressDto.builder()
                .id(address.getId())
                .apartment("test")
                .building("test")
                .city("test")
                .country("test")
                .street("test")
                .zip("test")
                .build();

        userService.editAddress(addressDto);
        assertEquals("test", addressesRepository.findById(address.getId()).get().getCity());
    }

    @Test
    public void deleteAddressById() {
        userService.deleteAddressById(address.getId());
        assertFalse(addressesRepository.findById(address.getId()).isPresent());
    }

    @Test
    public void saveNewAddress() {
        AddressDto addressDto = AddressDto.builder()
                .apartment("test")
                .building("test")
                .city("test")
                .country("test")
                .street("test")
                .zip("test")
                .build();
        userService.saveNewAddress(addressDto, user);
        assertTrue(addressesRepository.count()>1);
    }

    @After
    public void clear(){
        addressesRepository.deleteAll();
        userRepository.deleteAll();
        user = null;

    }
}