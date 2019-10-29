package com.rychkov.eshop.controllers;

import com.rychkov.eshop.dtos.CartItem;
import com.rychkov.eshop.dtos.OrderInfoDto;
import com.rychkov.eshop.entitys.Order;
import com.rychkov.eshop.exceptions.ProcessOrderException;
import com.rychkov.eshop.repositorys.AddressesRepository;
import com.rychkov.eshop.repositorys.DeliveryMethodsRepository;
import com.rychkov.eshop.repositorys.PaymentMethodsRepository;
import com.rychkov.eshop.repositorys.UserRepository;
import com.rychkov.eshop.services.OrderService;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
public class CheckoutPageController {
    @Autowired
    DeliveryMethodsRepository deliveryMethodsRepository;
    @Autowired
    PaymentMethodsRepository paymentMethodsRepository;
    @Autowired
    AddressesRepository addressesRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    OrderService orderService;


    @GetMapping("/checkout")
    public String checkoutPage(HttpSession session, Model model, Principal principal) {
        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");

        float total = 0;
        model.addAttribute("cart", cart);
        if (cart != null) {
            for (CartItem cartItem : cart) {
                total += cartItem.getBook().getPrice() * cartItem.getQuantity();
            }
            model.addAttribute("total", total);
        }

        model.addAttribute("addresses", addressesRepository.findAllOrderByUser(userRepository.findByUsername(principal.getName())));
        model.addAttribute("deliveryMethods", deliveryMethodsRepository.findAll());
        model.addAttribute("paymentMethods", paymentMethodsRepository.findAll());
        return "checkout";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/checkout/processOrder")
    @ResponseBody
    public JSONObject processOrder(@RequestBody OrderInfoDto orderInfo, Principal principal, HttpSession session) {
        JSONObject result = new JSONObject();
        orderInfo.setUser(userRepository.findByUsername(principal.getName()));
        orderInfo.setCartItems((List<CartItem>) session.getAttribute("cart"));

        try {
            Order order = orderService.newOrder(orderInfo, session);
            if (order == null) {
                result.put("error", true);
                result.put("message", "Failed to register new order");
                return result;
            }
        } catch (ProcessOrderException e) {
            result.put("error", true);
            result.put("message", e.getMessage());
            return result;
        }
        session.setAttribute("cart", null);
        session.setAttribute("orderId", null);
        result.put("message", "Your order registered!");
        result.put("error", false);
        return result;
    }
}
