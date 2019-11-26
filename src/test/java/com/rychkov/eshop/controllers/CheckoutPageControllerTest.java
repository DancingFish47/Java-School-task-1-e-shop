package com.rychkov.eshop.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rychkov.eshop.configurations.AppConfiguration;
import com.rychkov.eshop.configurations.PersistenceJPAConfig;
import com.rychkov.eshop.dtos.Cart;
import com.rychkov.eshop.repositories.AddressesRepository;
import com.rychkov.eshop.repositories.DeliveryMethodsRepository;
import com.rychkov.eshop.repositories.PaymentMethodsRepository;
import com.rychkov.eshop.repositories.UserRepository;
import com.rychkov.eshop.services.OrderService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;

import javax.servlet.http.HttpSession;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppConfiguration.class, PersistenceJPAConfig.class})
@WebAppConfiguration
public class CheckoutPageControllerTest extends AbstractJUnit4SpringContextTests {
    @InjectMocks
    CheckoutPageController checkoutPageController;
    @Autowired
    SpringTemplateEngine springTemplateEngine;
    @Mock
    private DeliveryMethodsRepository deliveryMethodsRepository;
    @Mock
    private PaymentMethodsRepository paymentMethodsRepository;
    @Mock
    private AddressesRepository addressesRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private OrderService orderService;
    @Mock
    private RabbitTemplate template;
    @Mock
    private HttpSession httpSession;
    @Mock
    private Cart cart;
    @Mock
    private Model model;
    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @Before
    public void setUp() {
        objectMapper = new ObjectMapper();

        MockitoAnnotations.initMocks(this);

        ThymeleafViewResolver resolver = new ThymeleafViewResolver();
        resolver.setTemplateEngine(springTemplateEngine);

        this.mockMvc = MockMvcBuilders.standaloneSetup(checkoutPageController).setViewResolvers(resolver).build();
    }

    @Test
    public void checkoutPage() throws Exception {
//        when(httpSession.getAttribute(anyString())).thenReturn(null);
//        when(addressesRepository.findAllByAddressStatus_NameAndUser(any(), any())).thenReturn(new ArrayList<>());
//        when(deliveryMethodsRepository.findAll()).thenReturn(new ArrayList<>());
//        when(paymentMethodsRepository.findAll()).thenReturn(new ArrayList<>());
//        when(cart.getCartItems()).thenReturn(new ArrayList<>());
//        when(model.addAttribute(any(),any())).thenReturn(null);
//        mockMvc.perform(get("/checkout"))
//                .andExpect(model().attributeExists("addresses", "deliveryMethods", "paymentMethods", "cart"))
//                .andExpect(view().name("checkout"));
    }

    @Test
    public void processOrder() throws Exception {
//        when(httpSession.getAttribute(anyString())).thenReturn(0);
//        MvcResult result = mockMvc.perform(post("/checkout/processOrder")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(objectMapper.writeValueAsString(OrderInfoDto.builder().build())))
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andReturn();
//
//        String content = result.getResponse().getContentAsString();
//        assertTrue(content.contains("Your order has been"));
    }
}