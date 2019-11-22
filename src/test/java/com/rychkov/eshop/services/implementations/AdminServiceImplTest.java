package com.rychkov.eshop.services.implementations;

import com.rychkov.eshop.configurations.AppConfiguration;
import com.rychkov.eshop.configurations.PersistenceJPAConfig;
import com.rychkov.eshop.entities.Book;
import com.rychkov.eshop.entities.User;
import com.rychkov.eshop.repositories.BooksRepository;
import com.rychkov.eshop.repositories.UserRepository;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppConfiguration.class, PersistenceJPAConfig.class})
@WebAppConfiguration
public class AdminServiceImplTest {
    @Autowired
    BooksRepository booksRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    AdminServiceImpl adminService;

    @Test
    public void getTopBooksList() {
        Book book = new Book();
        book.setSold(100);
        book.setPrice(100f);
        book.setName("Test");
        booksRepository.save(book);
        assertNotNull(adminService.getTopBooksList());
    }

    @Test
    public void getTopUsersList() {
        User user = User.builder().build();
        userRepository.save(user);
        assertNotNull(adminService.getTopUsersList());
    }

    @After
    public void clear() {
        booksRepository.deleteAll();
        userRepository.deleteAll();
    }
}