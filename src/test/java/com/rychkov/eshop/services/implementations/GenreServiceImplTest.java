package com.rychkov.eshop.services.implementations;

import com.rychkov.eshop.configurations.AppConfiguration;
import com.rychkov.eshop.configurations.PersistenceJPAConfig;
import com.rychkov.eshop.repositorys.BookCategoryRepository;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.junit.Assert.*;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppConfiguration.class, PersistenceJPAConfig.class})
@WebAppConfiguration
public class GenreServiceImplTest {
    @Autowired
    private BookCategoryRepository bookCategoryRepository;
    @Autowired
    private GenreServiceImpl genreService;

    @Test
    public void addGenre() {
        genreService.addGenre("Test2");
        assertNotNull(bookCategoryRepository.findByName("Test2"));
    }


    @Test
    public void editGenre() {
        genreService.addGenre("EditTest");
        int editId = bookCategoryRepository.findByName("EditTest").getId();
        genreService.editGenre(editId, "EditedTest");
        assertNotNull(bookCategoryRepository.findById(editId));
        assertEquals("EditedTest", bookCategoryRepository.findById(editId).get().getName());
    }


    @Test
    public void deleteGenre() {
        genreService.addGenre("DeleteGenre");
        int deleteId = bookCategoryRepository.findByName("DeleteGenre").getId();

        assertNotNull(bookCategoryRepository.findByName("DeleteGenre"));
        genreService.deleteGenre(deleteId);
        assertFalse(bookCategoryRepository.findById(deleteId).isPresent());
    }

    @After
    public void clear() {
        bookCategoryRepository.deleteAll();
    }
}