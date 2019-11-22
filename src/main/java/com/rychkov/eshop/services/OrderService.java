package com.rychkov.eshop.services;

import com.rychkov.eshop.dtos.*;
import com.rychkov.eshop.entities.Order;
import com.rychkov.eshop.exceptions.FailedToChangeStatusException;
import com.rychkov.eshop.exceptions.FailedToRepeatOrderException;
import com.rychkov.eshop.exceptions.ProcessOrderException;
import com.rychkov.eshop.exceptions.ReturnBooksToStockException;

import java.util.List;
/**
 * Interface for orders related methods.
 *
 * @author Roman Rychkov
 * @see #completeOrder(OrderInfoDto, Integer)
 * @see #findUserOrders(String)
 * @see #findAllOrders()
 * @see #returnBooks(Order)
 * @see #repeatCartFromOrder(Integer)
 * @see #changeOrderStatus(NewStatusDto)
 * @see #changePaymentStatus(NewStatusDto)
 *
 */
public interface OrderService {

    /**
     * Completes already existing temp order.
     * @param orderInfoDto Object contains information for order.
     * @see OrderInfoDto
     *
     * @param orderId Id of existing temp order to complete.
     * @throws ProcessOrderException when something happened during persisting data or temp order is already deleted by timeout.
     */
    void completeOrder(OrderInfoDto orderInfoDto, Integer orderId) throws ProcessOrderException;

    /**
     * Gets all orders for user.
     * @param username User's username.
     * @return List of OrderAndBooks objects for user.
     * @see OrderAndBooks
     */
    List<OrderAndBooks> findUserOrders(String username);

    /**
     * Gets all orders.
     * @return List of all OrderAndBooks objects.
     * @see OrderAndBooks
     */
    List<OrderAndBooks> findAllOrders();

    /**
     * Replaces user's cart with new cart, containing books from chosen order.
     * @param orderId Id of the order to repeat.
     * @return List of CartItems objects.
     * @see CartItem
     *
     * @throws FailedToRepeatOrderException when something happened during persisting data.
     */
    List<CartItem> repeatCartFromOrder(Integer orderId) throws FailedToRepeatOrderException;


    /**
     * Changes order status of existing order.
     * @param newStatusDto NewStatusDto object with information about new status.
     * @see NewStatusDto
     *
     * @throws FailedToChangeStatusException when something happened during persisting data.
     */
    void changeOrderStatus(NewStatusDto newStatusDto) throws FailedToChangeStatusException;

    /**
     * Changes payment status of existing order.
     * @param newStatusDto NewStatusDto object with information about new status.
     * @see NewStatusDto
     *
     * @throws FailedToChangeStatusException when something happened during persisting data.
     */
    void changePaymentStatus(NewStatusDto newStatusDto) throws FailedToChangeStatusException;

    /**
     * Returns books to stocks if order was not completed or timed out.
     * @param order Order object with temp status to revert.
     * @throws ReturnBooksToStockException when something happened during persisting data.
     */
    void returnBooks(Order order) throws ReturnBooksToStockException;
}
