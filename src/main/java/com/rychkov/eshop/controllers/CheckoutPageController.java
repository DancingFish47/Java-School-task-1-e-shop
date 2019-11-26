package com.rychkov.eshop.controllers;

import com.rychkov.eshop.dtos.Cart;
import com.rychkov.eshop.dtos.CartItem;
import com.rychkov.eshop.dtos.OrderInfoDto;
import com.rychkov.eshop.dtos.ResponseDto;
import com.rychkov.eshop.entities.Book;
import com.rychkov.eshop.exceptions.ProcessOrderException;
import com.rychkov.eshop.repositories.*;
import com.rychkov.eshop.services.OrderService;
import lombok.RequiredArgsConstructor;
import net.minidev.json.JSONObject;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.Optional;

import static com.rychkov.eshop.configurations.AppConfiguration.ADDRESS_EXISTS;
import static com.rychkov.eshop.configurations.RabbitConfiguration.EDIT_QUEUE_NAME;

@RequiredArgsConstructor
@Controller
public class CheckoutPageController {
    private final DeliveryMethodsRepository deliveryMethodsRepository;
    private final PaymentMethodsRepository paymentMethodsRepository;
    private final AddressesRepository addressesRepository;
    private final UserRepository userRepository;
    private final OrderService orderService;

    private final RabbitTemplate template;



    @GetMapping("/checkout")
    public String checkoutPage(HttpSession session, Model model, Principal principal) {
        Cart cart = (Cart) session.getAttribute("cart");

        float total = 0;
        model.addAttribute("cart", cart.getCartItems());
        if (cart.getCartItems() != null) {
            for (CartItem cartItem : cart.getCartItems()) {
                total += cartItem.getBook().getPrice() * cartItem.getQuantity();
            }
            model.addAttribute("total", total);
        }

        model.addAttribute("addresses", addressesRepository.findAllByAddressStatus_NameAndUser(ADDRESS_EXISTS ,userRepository.findByUsername(principal.getName())));
        model.addAttribute("deliveryMethods", deliveryMethodsRepository.findAll());
        model.addAttribute("paymentMethods", paymentMethodsRepository.findAll());
        return "checkout";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/checkout/processOrder")
    @ResponseBody
    public ResponseDto processOrder(@RequestBody OrderInfoDto orderInfo, Principal principal, HttpSession session) throws ProcessOrderException {

        Cart cart = (Cart) session.getAttribute("cart");
        Integer orderId = (Integer) session.getAttribute("orderId");
        if((cart == null) || (orderId == null)) throw new ProcessOrderException("Something went wrong");

        orderInfo.setUser(userRepository.findByUsername(principal.getName()));
        orderInfo.setCartItems(cart.getCartItems());
        orderService.completeOrder(orderInfo, orderId);

        for (CartItem cartItem : orderInfo.getCartItems()){
            cartItem.getBook().setAmount(cartItem.getBook().getAmount()-cartItem.getQuantity());
            template.convertAndSend(EDIT_QUEUE_NAME, cartItem.getBook());
        }

        session.setAttribute("cart", null);
        session.setAttribute("orderId", null);
        return ResponseDto.builder()
                .error(false)
                .message("Your order has been registered!")
                .build();
    }
}
