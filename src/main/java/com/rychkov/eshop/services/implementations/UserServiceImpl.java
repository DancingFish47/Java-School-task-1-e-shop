package com.rychkov.eshop.services.implementations;

import com.rychkov.eshop.dtos.AddressDto;
import com.rychkov.eshop.dtos.PasswordDto;
import com.rychkov.eshop.dtos.UserDto;
import com.rychkov.eshop.dtos.UserMainInfoDto;
import com.rychkov.eshop.entities.Address;
import com.rychkov.eshop.entities.User;
import com.rychkov.eshop.exceptions.*;
import com.rychkov.eshop.repositories.AddressStatusRepository;
import com.rychkov.eshop.repositories.AddressesRepository;
import com.rychkov.eshop.repositories.UserRepository;
import com.rychkov.eshop.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

import static com.rychkov.eshop.configurations.AppConfiguration.ADDRESS_DELETED;
import static com.rychkov.eshop.configurations.AppConfiguration.ADDRESS_EXISTS;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final AddressesRepository addressesRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AddressStatusRepository addressStatusRepository;


    @Transactional
    @Override
    public void registerNewUser(UserDto userDto) throws EmailExistsException, UsernameExistsException {
        if (emailExists(userDto.getEmail())) throw new EmailExistsException("This email is already registered");
        if (usernameExists(userDto.getUsername()))
            throw new UsernameExistsException("This username is already registered");

        User user = User.builder()
                .userRole("User")
                .birthdate(userDto.getBirthdate())
                .email(userDto.getEmail())
                .firstName(userDto.getFirstName())
                .lastName(userDto.getLastName())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .username(userDto.getUsername())
                .build();

        userRepository.save(user);
    }

    @Override
    public void editMainInfo(UserMainInfoDto dto, Integer userId) throws JsonException {
        Optional<User> optionalUser = userRepository.findById(userId);

        User user;
        if (optionalUser.isPresent()) user = optionalUser.get();
        else throw new JsonException("User not found");
        user.setFirstName(dto.getFirstname());
        user.setLastName(dto.getLastname());
        user.setBirthdate(dto.getBirthdate());
        userRepository.save(user);
    }

    @Override
    public void changePassword(PasswordDto passwordDto, Integer userId) throws PasswordMismatchException {
        String currentPassword = passwordDto.getCurrentPassword();
        Optional<User> optionalUser = userRepository.findById(userId);
        User user;

        if (optionalUser.isPresent()) user = optionalUser.get();
        else throw new UsernameNotFoundException("User not found");

        if (passwordEncoder.matches(currentPassword, user.getPassword())) {
            user.setPassword(passwordEncoder.encode(passwordDto.getNewPassword()));
        } else throw new PasswordMismatchException("Wrong current password!");

        userRepository.save(user);
    }

    @Override
    public Address editAddress(AddressDto addressDto) {
        Optional<Address> optionalAddress = addressesRepository.findById(addressDto.getId());
        Address address;
        if (optionalAddress.isPresent()) {
            address = optionalAddress.get();
            address.setCountry(addressDto.getCountry());
            address.setApartment(addressDto.getApartment());
            address.setBuilding(addressDto.getBuilding());
            address.setCity(addressDto.getCity());
            address.setStreet(addressDto.getStreet());
            address.setZip(addressDto.getZip());

            addressesRepository.save(address);
        } else return null;

        return address;
    }

    @Override
    public void deleteAddressById(Integer addressId) throws FailedToDeleteAddressException {
        Optional<Address> deleteAddress = addressesRepository.findById(addressId);
        if (deleteAddress.isPresent()) {
            Address address = deleteAddress.get();
            address.setAddressStatus(addressStatusRepository.findByName(ADDRESS_DELETED));
            addressesRepository.save(address);
        } else throw new FailedToDeleteAddressException("Failed to delete address");
    }

    @Override
    public Address saveNewAddress(AddressDto newAddress, User user) {
        Address address = Address.builder()
                .country(newAddress.getCountry())
                .city(newAddress.getCity())
                .apartment(newAddress.getApartment())
                .building(newAddress.getBuilding())
                .street(newAddress.getStreet())
                .user(user)
                .zip(newAddress.getZip())
                .addressStatus(addressStatusRepository.findByName(ADDRESS_EXISTS))
                .build();

        return addressesRepository.save(address);
    }


    private boolean emailExists(String email) {
        User user = userRepository.findByEmail(email.toLowerCase());
        return user != null;
    }

    private boolean usernameExists(String username) {
        User user = userRepository.findByUsername(username);
        return user != null;
    }
}
