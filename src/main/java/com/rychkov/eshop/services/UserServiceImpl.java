package com.rychkov.eshop.services;

import com.rychkov.eshop.dtos.UserDto;
import com.rychkov.eshop.entitys.Address;
import com.rychkov.eshop.entitys.User;
import com.rychkov.eshop.exceptions.EmailExistsException;
import com.rychkov.eshop.exceptions.PasswordMismatchException;
import com.rychkov.eshop.exceptions.UsernameExistsException;
import com.rychkov.eshop.repositorys.AddressesRepository;
import com.rychkov.eshop.repositorys.UserRepository;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private AddressesRepository addressesRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Transactional
    @Override
    public User registerNewUser(UserDto userDto) throws EmailExistsException, UsernameExistsException{
        if(emailExists(userDto.getEmail())) throw new EmailExistsException("This email is already registered");
        if(usernameExists(userDto.getUsername())) throw new UsernameExistsException("This username is already registered");
        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setBirthdate(userDto.getBirthdate());
        user.setEmail(userDto.getEmail().toLowerCase());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setUserRole("User");
        return userRepository.save(user);
    }

    @Override
    public User changeMainSettings(JSONObject edit, Integer userId) {
        Optional<User> optionalUser = userRepository.findById(userId);

        User user;
        if(optionalUser.isPresent()){
            user = optionalUser.get();
        }else{
            return null;
        }
        user.setFirstName((String) edit.get("firstname"));
        user.setLastName((String) edit.get("lastname"));
        user.setBirthdate((String) edit.get("birthdate"));
        userRepository.save(user);
        return user;
    }

    @Override
    public void changePassword(JSONObject edit, Integer userId) throws PasswordMismatchException {
        String currentPassword = (String) edit.get("currentPassword");
        Optional<User> optionalUser = userRepository.findById(userId);
        User user;

        if (optionalUser.isPresent()) user = optionalUser.get();
        else throw new UsernameNotFoundException("User not found");

        if (passwordEncoder.matches(currentPassword, user.getPassword())){
            user.setPassword(passwordEncoder.encode((String) edit.get("newPassword")));
        } else throw new PasswordMismatchException("Wrong current password!");

        userRepository.save(user);
    }

    @Override
    public Address editAddress(JSONObject edit, Integer id) {
        Optional<Address> optionalAddress = addressesRepository.findById(id);
        Address address;
        if (optionalAddress.isPresent()){
            address = optionalAddress.get();
            address.setCountry((String) edit.get("country"));
            address.setCity((String) edit.get("city"));
            address.setStreet((String) edit.get("street"));
            address.setBuilding((String) edit.get("building"));
            address.setApartment((String) edit.get("apartment"));
            address.setZip((String) edit.get("zip"));
            addressesRepository.save(address);
        }else {
            return null;
        }
        return address;
    }

    @Override
    public boolean deleteAddressById(Integer addressId) {
        Optional<Address> deleteAddress = addressesRepository.findById(addressId);

        if(deleteAddress.isPresent()){
            addressesRepository.delete(deleteAddress.get());
        } else return false;

        Optional<Address> address = addressesRepository.findById(addressId);
        return !address.isPresent();
    }

    @Override
    public Address saveNewAddress(JSONObject newAddress, User user) {
        Address address = new Address();
        address.setCountry((String) newAddress.get("country"));
        address.setCity((String) newAddress.get("city"));
        address.setStreet((String) newAddress.get("street"));
        address.setBuilding((String) newAddress.get("building"));
        address.setApartment((String) newAddress.get("apartment"));
        address.setZip((String) newAddress.get("zip"));
        address.setUser(user);
        return addressesRepository.save(address);
    }


    private boolean emailExists(String email) {
        User user = userRepository.findByEmail(email.toLowerCase());
        if (user != null) {
            return true;
        }
        return false;
    }
    private boolean usernameExists(String username) {
        User user = userRepository.findByUsername(username);
        if (user != null) {
            return true;
        }
        return false;
    }
}
