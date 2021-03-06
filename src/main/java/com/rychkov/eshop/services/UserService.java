package com.rychkov.eshop.services;

import com.rychkov.eshop.dtos.AddressDto;
import com.rychkov.eshop.dtos.PasswordDto;
import com.rychkov.eshop.dtos.UserDto;
import com.rychkov.eshop.dtos.UserMainInfoDto;
import com.rychkov.eshop.entities.Address;
import com.rychkov.eshop.entities.User;
import com.rychkov.eshop.exceptions.EmailExistsException;
import com.rychkov.eshop.exceptions.FailedToDeleteAddressException;
import com.rychkov.eshop.exceptions.PasswordMismatchException;
import com.rychkov.eshop.exceptions.UsernameExistsException;

/**
 * Interface for user settings related methods.
 *
 * @author Roman Rychkov
 * @see #registerNewUser(UserDto)
 * @see #editMainInfo(UserMainInfoDto, Integer)
 * @see #changePassword(PasswordDto, Integer)
 * @see #editAddress(AddressDto)
 * @see #deleteAddressById(Integer)
 * @see #saveNewAddress(AddressDto, User)
 */

public interface UserService {

    /**
     * Registers new user.
     *
     * @param userDto Object, contains information about new user.
     * @throws EmailExistsException    when this email already exists.
     * @throws UsernameExistsException when this username already exists.
     * @see UserDto
     */
    void registerNewUser(UserDto userDto) throws EmailExistsException, UsernameExistsException;

    /**
     * Edits main info about user: first name, last name, birthday date.
     *
     * @param userMainInfoDto Contains new information for user.
     * @param userId          Id of the editing user.
     * @see UserMainInfoDto
     */
    void editMainInfo(UserMainInfoDto userMainInfoDto, Integer userId);

    /**
     * Changes password for user.
     *
     * @param passwordDto Contains current password and new password for encrypted matching.
     * @param userId      Id of the editing user.
     * @throws PasswordMismatchException When current password didn't match.
     * @see PasswordDto
     */
    void changePassword(PasswordDto passwordDto, Integer userId) throws PasswordMismatchException;

    /**
     * Edits user's address' information.
     *
     * @param addressDto Contains new address information.
     * @return Edited address.
     * @see AddressDto
     */
    Address editAddress(AddressDto addressDto);

    /**
     * Deletes user's address by id.
     *
     * @param addressId Id of the address to delete.
     * @throws FailedToDeleteAddressException when something happened during perstisting data.
     */
    void deleteAddressById(Integer addressId) throws FailedToDeleteAddressException;

    /**
     * Saves new address for User.
     *
     * @param addressDto Contains information for new address.
     * @param user       User for new address.
     * @return Saved address.
     * @see AddressDto
     */
    Address saveNewAddress(AddressDto addressDto, User user);
}
