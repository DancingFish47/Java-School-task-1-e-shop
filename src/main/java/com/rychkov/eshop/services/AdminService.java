package com.rychkov.eshop.services;

import com.rychkov.eshop.dtos.TopBookDto;
import com.rychkov.eshop.dtos.TopUserDto;

import java.util.List;

/**
 * Interface for admin/manager related methods.
 *
 * @author Roman Rychkov
 * @see #getTopBooksList()
 * @see #getTopUsersList()
 */
public interface AdminService {
    /**
     * Gets statistics for books.
     *
     * @return List of TopBookDto objects, containing name, amount sold and total profit for each book.
     */
    List<TopBookDto> getTopBooksList();

    /**
     * Gets statistics for users.
     *
     * @return list of TopUserDto objects, containing username, amount of books bought and total value of bought books.
     */
    List<TopUserDto> getTopUsersList();
}
