package com.rychkov.eshop.services.implementations;

import com.rychkov.eshop.configurations.AppConfiguration;
import com.rychkov.eshop.configurations.PersistenceJPAConfig;
import com.rychkov.eshop.dtos.AddItemDto;
import com.rychkov.eshop.dtos.CartItem;
import com.rychkov.eshop.entitys.Book;
import com.rychkov.eshop.repositorys.BooksRepository;
import com.rychkov.eshop.repositorys.OrderStatusRepository;
import com.rychkov.eshop.repositorys.OrdersRepository;
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

import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppConfiguration.class, PersistenceJPAConfig.class})
@WebAppConfiguration
public class CartServiceImplTest {
    @Autowired
    BooksRepository booksRepository;
    @Autowired
    @InjectMocks
    CartService cartService;
    @Mock
    HttpSession httpSession;
    @Autowired
    OrdersRepository ordersRepository;
    @Autowired
    OrderStatusRepository orderStatusRepository;

    private Book savedbook;

    @Before
    public void setUp() {
        httpSession = mock(HttpSession.class);

        Book book = new Book();
        book.setName("CartAddTest");
        book.setAmount(100);
        savedbook = booksRepository.save(book);

        List<CartItem> cart = new ArrayList<>();
        CartItem cartItem = new CartItem(savedbook, 1);
        cart.add(cartItem);

        when(httpSession.getAttribute("cart")).thenReturn(cart);
    }

    @Test
    public void addItem() {

        AddItemDto addItemDto = new AddItemDto();
        addItemDto.setId(savedbook.getId());
        addItemDto.setQuantity(1);

        assertFalse((Boolean) cartService.addItem(httpSession, addItemDto).get("error"));
    }

    @Test
    public void deleteItem() {
        assertFalse((Boolean) cartService.deleteItem(httpSession, savedbook.getId()).get("error"));
    }

    @After
    public void clear() {
        booksRepository.deleteAll();
        savedbook = null;
    }

}