package com.rychkov.eshop.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rychkov.eshop.configurations.AppConfiguration;
import com.rychkov.eshop.configurations.PersistenceJPAConfig;
import com.rychkov.eshop.dtos.AddItemDto;
import com.rychkov.eshop.dtos.PageParams;
import com.rychkov.eshop.repositories.BookCategoryRepository;
import com.rychkov.eshop.repositories.OrdersRepository;
import com.rychkov.eshop.services.BookService;
import com.rychkov.eshop.services.CartService;
import com.rychkov.eshop.services.OrderService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppConfiguration.class, PersistenceJPAConfig.class})
@WebAppConfiguration
public class BooksPageControllerTest extends AbstractJUnit4SpringContextTests {
    @InjectMocks
    BooksPageController booksPageController;
    @Autowired
    SpringTemplateEngine springTemplateEngine;
    @Mock
    private BookService bookService;
    @Mock
    private BookCategoryRepository bookCategoryRepository;
    @Mock
    private CartService cartService;
    @Mock
    private OrdersRepository ordersRepository;
    @Mock
    private OrderService orderService;
    @Mock
    private HttpSession httpSession;
    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @Before
    public void setUp() {
        objectMapper = new ObjectMapper();

        MockitoAnnotations.initMocks(this);

        ThymeleafViewResolver resolver = new ThymeleafViewResolver();
        resolver.setTemplateEngine(springTemplateEngine);

        this.mockMvc = MockMvcBuilders.standaloneSetup(booksPageController).setViewResolvers(resolver).build();
    }

    @Test
    public void showBooks() throws Exception {
        when(httpSession.getAttribute(anyString())).thenReturn(null);
        when(bookService.prepareBooksList(any(PageParams.class))).thenReturn(Page.empty());
        when(bookCategoryRepository.findAll()).thenReturn(new ArrayList<>());

        mockMvc.perform(get("/books/category").param("sort", "sort"))
                .andExpect(model().attributeExists("BookCategory", "sort", "category", "books", "page", "nextpage", "prevpage"))
                .andExpect(view().name("books"));

    }

    @Test
    public void addToCart() throws Exception {
        MvcResult result = mockMvc.perform(post("/addToCart")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(new AddItemDto())))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        String content = result.getResponse().getContentAsString();
        assertTrue(content.contains("Book has been"));
    }
}