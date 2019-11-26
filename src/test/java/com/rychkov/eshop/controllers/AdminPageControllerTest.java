package com.rychkov.eshop.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rychkov.eshop.configurations.AppConfiguration;
import com.rychkov.eshop.configurations.PersistenceJPAConfig;
import com.rychkov.eshop.dtos.BookDto;
import com.rychkov.eshop.dtos.NewStatusDto;
import com.rychkov.eshop.entities.Book;
import com.rychkov.eshop.entities.BookCategory;
import com.rychkov.eshop.repositories.BookCategoryRepository;
import com.rychkov.eshop.repositories.BooksRepository;
import com.rychkov.eshop.repositories.OrderStatusRepository;
import com.rychkov.eshop.repositories.PaymentStatusRepository;
import com.rychkov.eshop.services.AdminService;
import com.rychkov.eshop.services.BookService;
import com.rychkov.eshop.services.GenreService;
import com.rychkov.eshop.services.OrderService;
import net.minidev.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
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

import java.util.Optional;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppConfiguration.class, PersistenceJPAConfig.class})
@WebAppConfiguration
public class AdminPageControllerTest extends AbstractJUnit4SpringContextTests {
    @InjectMocks
    AdminPageController adminPageController;
    @Autowired
    SpringTemplateEngine springTemplateEngine;
    private MockMvc mockMvc;
    @Mock
    private OrderService orderService;
    @Mock
    private OrderStatusRepository orderStatusRepository;
    @Mock
    private PaymentStatusRepository paymentStatusRepository;
    @Mock
    private AdminService adminService;
    @Mock
    private BooksRepository booksRepository;
    @Mock
    private BookCategoryRepository bookCategoryRepository;
    @Mock
    private BookService bookService;
    @Mock
    private GenreService genreService;
    private ObjectMapper objectMapper;

    @Before
    public void setUp() {
        objectMapper = new ObjectMapper();

        MockitoAnnotations.initMocks(this);

        ThymeleafViewResolver resolver = new ThymeleafViewResolver();
        resolver.setTemplateEngine(springTemplateEngine);

        this.mockMvc = MockMvcBuilders.standaloneSetup(adminPageController).setViewResolvers(resolver).build();
    }

    @Test
    public void adminPage() throws Exception {
        mockMvc.perform(get("/adminPage"))
                .andExpect(status().isOk())
                .andExpect(view().name("adminOrders"));
    }

    @Test
    public void adminOrdersView() throws Exception {
        mockMvc.perform(get("/adminPage/adminOrders"))
                .andExpect(model().attributeExists("ordersAndBooks"))
                .andExpect(model().attributeExists("orderStatus"))
                .andExpect(model().attributeExists("paymentStatus"))
                .andExpect(view().name("adminOrders"));
    }

    @Test
    public void adminStatsView() throws Exception {
        mockMvc.perform(get("/adminPage/adminStats"))
                .andExpect(model().attributeExists("topBooks"))
                .andExpect(model().attributeExists("topUsers"))
                .andExpect(view().name("adminStats"));
    }

    @Test
    public void adminManageBooksView() throws Exception {
        mockMvc.perform(get("/adminPage/adminManageBooks"))
                .andExpect(model().attributeExists("allBooks"))
                .andExpect(model().attributeExists("genres"))
                .andExpect(view().name("adminManageBooks"));
    }

    @Test
    public void adminManageCategoriesView() throws Exception {
        mockMvc.perform(get("/adminPage/adminManageCategories"))
                .andExpect(model().attributeExists("genres"))
                .andExpect(view().name("adminManageCategories"));
    }

    @Test
    public void changeOrderStatus() throws Exception {
        MvcResult result = mockMvc.perform(post("/adminPage/changeOrderStatus")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(new NewStatusDto())))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        String content = result.getResponse().getContentAsString();
        assertTrue(content.contains("Order"));
    }

    @Test
    public void changePaymentStatus() throws Exception {
        MvcResult result = mockMvc.perform(post("/adminPage/changePaymentStatus")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(new NewStatusDto())))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        String content = result.getResponse().getContentAsString();
        assertTrue(content.contains("Payment"));
    }

    @Test
    public void getGenreById() throws Exception {
        Optional<BookCategory> optionalBookCategory = Optional.of(new BookCategory());
        when(bookCategoryRepository.findById(anyInt())).thenReturn(optionalBookCategory);

        MvcResult result = mockMvc.perform(post("/adminPage/adminManageCategories/getGenreById")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(0)))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        String content = result.getResponse().getContentAsString();
        assertTrue(content.contains("genre"));
    }

    @Test
    public void deleteGenreById() throws Exception {
        MvcResult result = mockMvc.perform(post("/adminPage/adminManageCategories/deleteGenre")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(0)))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        String content = result.getResponse().getContentAsString();
        assertTrue(content.contains("Genre deleted!"));
    }

    @Test
    public void saveEditGenre() throws Exception {
        MvcResult result = mockMvc.perform(post("/adminPage/adminManageCategories/saveEditGenre")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(new JSONObject())))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        String content = result.getResponse().getContentAsString();
        assertTrue(content.contains("Genre edited!"));
    }

    @Test
    public void saveNewGenre() throws Exception {
        MvcResult result = mockMvc.perform(post("/adminPage/adminManageCategories/saveNewGenre")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(0)))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        String content = result.getResponse().getContentAsString();
        assertTrue(content.contains("Genre saved!"));
    }

    @Test
    public void saveNewBook() throws Exception {
        MvcResult result = mockMvc.perform(post("/adminPage/adminManageBooks/saveNewBook")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(new BookDto())))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        String content = result.getResponse().getContentAsString();
        assertTrue(content.contains("New book"));
    }

    @Test
    public void saveEditBook() throws Exception {
        MvcResult result = mockMvc.perform(post("/adminPage/adminManageBooks/saveEditBook")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(new BookDto())))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        String content = result.getResponse().getContentAsString();
        assertTrue(content.contains("Book details"));
    }

    @Test
    public void getBookById() throws Exception {
        Optional<Book> optionalBook = Optional.of(new Book());
        when(booksRepository.findById(anyInt())).thenReturn(optionalBook);

        MvcResult result = mockMvc.perform(post("/adminPage/adminManageBooks/getBookById")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(0)))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        String content = result.getResponse().getContentAsString();
        assertTrue(content.contains("book"));
    }

    @Test
    public void deleteBookById() throws Exception {
        MvcResult result = mockMvc.perform(post("/adminPage/adminManageBooks/deleteBook")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString("0")))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        String content = result.getResponse().getContentAsString();
        assertTrue(content.contains("Book deleted"));
    }
}