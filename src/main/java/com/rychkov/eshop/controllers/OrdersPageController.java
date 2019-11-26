package com.rychkov.eshop.controllers;

import com.rychkov.eshop.dtos.Cart;
import com.rychkov.eshop.dtos.ResponseDto;
import com.rychkov.eshop.exceptions.FailedToRepeatOrderException;
import com.rychkov.eshop.services.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class OrdersPageController {
    private final OrderService orderService;

    @RequestMapping(value = "/orders", method = RequestMethod.GET)
    public String ordersView(Principal principal, Model model) {
        model.addAttribute("orderAndBooks", orderService.findUserOrders(principal.getName()));
        return "orders";
    }

    @RequestMapping(value = "/repeatOrder", method = RequestMethod.POST)
    @ResponseBody
    public ResponseDto repeatOrder(@RequestBody Integer orderId, HttpSession session) throws FailedToRepeatOrderException {
        Cart cart = new Cart();
        cart.setCartItems(orderService.repeatCartFromOrder(orderId));
        session.setAttribute("cart", cart);
        return ResponseDto.builder()
                .message("Chosen order has been copied to your cart!")
                .build();
    }


}
