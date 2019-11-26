package com.rychkov.eshop.services;

import com.rychkov.eshop.dtos.AddItemDto;
import com.rychkov.eshop.dtos.Cart;
import com.rychkov.eshop.exceptions.BookException;
import com.rychkov.eshop.exceptions.OutOfStockException;
import com.rychkov.eshop.exceptions.ProcessOrderException;

/**
 * Interface for cart related methods.
 *
 * @author Roman Rychkov
 * @see #addItem(Cart, AddItemDto)
 * @see #deleteItem(Cart, Integer)
 * @see #checkStocksAndCreateTempOrder(Cart)
 */
public interface CartService {

    /**
     * Adds new item in cart.
     *
     * @param cart Cart object to put items in, contains list of CartItems.
     * @param item Item object to put, contains book id and amount to add.
     * @throws OutOfStockException there are not enough books left in stock.
     * @throws BookException       when something happened during persisting data.
     * @see com.rychkov.eshop.dtos.CartItem
     */
    void addItem(Cart cart, AddItemDto item) throws OutOfStockException, BookException;

    /**
     * Deletes item from cart.
     *
     * @param cart   Cart object to delete items from, contains list of CartItems.
     * @param bookId Id of the book to delete.
     * @throws BookException when something happened during persisting data.
     * @see com.rychkov.eshop.dtos.CartItem
     */
    void deleteItem(Cart cart, Integer bookId) throws BookException;

    /**
     * Creates new order in db, sets it's OrderStatus to TempOrder,
     * which will be deleted after some time unless finished.
     *
     * @param cart Cart object for new order, contains list of CartItems
     * @return Order id value, which will persist in HttpSession. Value is required later for finishing order.
     * @throws OutOfStockException   when there are not enough books left in stock.
     * @throws ProcessOrderException when something happened during persisting data.
     * @see Cart
     */
    int checkStocksAndCreateTempOrder(Cart cart) throws OutOfStockException, ProcessOrderException;
}
