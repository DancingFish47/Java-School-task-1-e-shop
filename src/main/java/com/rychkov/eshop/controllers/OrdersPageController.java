package com.rychkov.eshop.controllers;

import com.rychkov.eshop.dtos.CartItem;
import com.rychkov.eshop.exceptions.FailedToRepeatOrderException;
import com.rychkov.eshop.services.OrderService;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.List;

@Controller
public class OrdersPageController {
    @Autowired
    OrderService orderService;

    @RequestMapping(value = "/orders", method = RequestMethod.GET)
    public String ordersView(Principal principal, Model model) {
        model.addAttribute("orderAndBooks", orderService.findUserOrders(principal.getName()));
        return "orders";
    }

    @RequestMapping(value = "/repeatOrder", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject repeatOrder(@RequestBody Integer orderId, HttpSession session) throws FailedToRepeatOrderException {
        JSONObject result = new JSONObject();
        List<CartItem> repeatCart = orderService.repeatCartFromOrder(orderId);
        session.setAttribute("cart", repeatCart);
        result.put("message", "Chosen order was copied to your cart!");
        return result;
    }


}
