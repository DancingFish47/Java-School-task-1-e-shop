package com.rychkov.eshop.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rychkov.eshop.configurations.AppConfiguration;
import com.rychkov.eshop.configurations.PersistenceJPAConfig;
import com.rychkov.eshop.repositories.OrdersRepository;
import com.rychkov.eshop.services.CartService;
import com.rychkov.eshop.services.OrderService;
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

import javax.servlet.http.HttpSession;

import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppConfiguration.class, PersistenceJPAConfig.class})
@WebAppConfiguration
public class CartPageControllerTest extends AbstractJUnit4SpringContextTests {
    @InjectMocks
    CartPageController cartPageController;
    @Autowired
    SpringTemplateEngine springTemplateEngine;
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

        this.mockMvc = MockMvcBuilders.standaloneSetup(cartPageController).setViewResolvers(resolver).build();
    }

    @Test
    public void cartpage() throws Exception {
        when(httpSession.getAttribute(anyString())).thenReturn(null);
        mockMvc.perform(get("/cart").param("sort", "sort"))
                .andExpect(view().name("cart"));
    }

    @Test
    public void deleteFromCart() throws Exception {
        MvcResult result = mockMvc.perform(post("/deleteFromCart")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(0)))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        String content = result.getResponse().getContentAsString();
        assertTrue(content.contains("Book has been deleted"));
    }

    @Test
    public void checkStocks() throws Exception {
        when(cartService.checkStocksAndCreateTempOrder(any())).thenReturn(0);
        MvcResult result = mockMvc.perform(post("/checkStocks")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(0)))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        String content = result.getResponse().getContentAsString();
        assertTrue(content.contains("false"));
    }
}