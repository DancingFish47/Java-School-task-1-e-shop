package com.rychkov.eshop.services.implementations;

import com.rychkov.eshop.configurations.AppConfiguration;
import com.rychkov.eshop.configurations.PersistenceJPAConfig;
import com.rychkov.eshop.dtos.BookDto;
import com.rychkov.eshop.dtos.PageParams;
import com.rychkov.eshop.entities.Book;
import com.rychkov.eshop.repositories.BooksRepository;
import com.rychkov.eshop.services.BookService;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppConfiguration.class, PersistenceJPAConfig.class})
@WebAppConfiguration
public class BookServiceImplTest {
    @Autowired
    BookService bookService;
    @Autowired
    BooksRepository booksRepository;

    @Test
    public void prepareBooksList() {
        for (int i = 0; i < 3; i++) {
            Book book = new Book();
            book.setName("Test" + i);
            booksRepository.save(book);
        }
        PageParams pageParams = PageParams.builder().page(1).build();
        assertTrue(bookService.prepareBooksList(pageParams).getTotalElements() > 0);
    }

    @Test
    public void deleteBookById() {
        Book book = new Book();
        book.setName("DeleteTest");
        booksRepository.save(book);
        int deleteId = booksRepository.findByName("DeleteTest").get().getId();
        bookService.deleteBookById(deleteId);
        assertFalse(booksRepository.findById(deleteId).isPresent());
    }

    @Test
    public void addNewBook() {
        BookDto bookDto = new BookDto();
        bookDto.setName("AddTest");
        bookDto.setAmount(10);
        bookDto.setAuthor("Author");
        bookDto.setPages(10);
        bookDto.setPrice(10f);
        bookDto.setSold(10);
        bookDto.setGenre(null);
        bookService.addNewBook(bookDto);
        assertTrue(booksRepository.findByName("AddTest").isPresent());
    }

    @Test
    public void editBook() {
        BookDto bookDto = new BookDto();
        bookDto.setName("EditTest");
        bookDto.setAmount(10);
        bookDto.setAuthor("Author");
        bookDto.setPages(10);
        bookDto.setPrice(10f);
        bookDto.setSold(10);
        bookDto.setGenre(null);

        bookService.addNewBook(bookDto);

        Optional<Book> optionalBook = booksRepository.findByName("EditTest");
        assertTrue(optionalBook.isPresent());

        int editId = optionalBook.get().getId();

        bookDto.setId(editId);
        bookDto.setName("EditedBook");

        bookService.editBook(bookDto);

        assertEquals("EditedBook", booksRepository.findById(editId).get().getName());
    }

    @After
    public void clear() {
        booksRepository.deleteAll();
    }
}