package com.rychkov.eshop.services.implementations;

import com.rychkov.eshop.configurations.AppConfiguration;
import com.rychkov.eshop.configurations.PersistenceJPAConfig;
import com.rychkov.eshop.dtos.AddItemDto;
import com.rychkov.eshop.dtos.Cart;
import com.rychkov.eshop.dtos.CartItem;
import com.rychkov.eshop.entities.Book;
import com.rychkov.eshop.entities.OrderStatus;
import com.rychkov.eshop.repositories.BooksRepository;
import com.rychkov.eshop.repositories.OrderStatusRepository;
import com.rychkov.eshop.repositories.OrdersRepository;
import com.rychkov.eshop.services.CartService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppConfiguration.class, PersistenceJPAConfig.class})
@WebAppConfiguration
public class CartServiceImplTest {
    @Autowired
    BooksRepository booksRepository;
    @Autowired
    CartService cartService;
    @Autowired
    OrdersRepository ordersRepository;
    @Autowired
    OrderStatusRepository orderStatusRepository;

    private Book savedbook;

    @Before
    public void setUp() {

        Book book = new Book();
        book.setName("CartAddTest");
        book.setAmount(100);
        savedbook = booksRepository.save(book);

        OrderStatus status = new OrderStatus();
        status.setName("TEMPORDER");
        orderStatusRepository.save(status);

    }

    @Test
    public void addItem() {

        AddItemDto addItemDto = new AddItemDto();
        addItemDto.setId(savedbook.getId());
        addItemDto.setQuantity(1);

        Cart cart = new Cart();
        cartService.addItem(cart,addItemDto);
        assertNotNull(cart.getCartItems());
    }

    @Test
    public void deleteItem() {
        Cart cart = new Cart();
        List<CartItem> cartItems = cart.getCartItems();
        cartItems.add(new CartItem(savedbook, 1));
        cartService.deleteItem(cart, savedbook.getId());
        assertTrue(cart.getCartItems().isEmpty());
    }

    @Test
    public void checkStocksAndCreateTempOrder(){
        Cart cart = new Cart();
        List<CartItem> cartItems = cart.getCartItems();
        cartItems.add(new CartItem(savedbook, 1));
        cartService.checkStocksAndCreateTempOrder(cart);
        assertNotNull(ordersRepository.findAllByOrderStatus_Name("TEMPORDER"));
    }
    @After
    public void clear() {
        ordersRepository.deleteAll();
        booksRepository.deleteAll();
        savedbook = null;
    }

}