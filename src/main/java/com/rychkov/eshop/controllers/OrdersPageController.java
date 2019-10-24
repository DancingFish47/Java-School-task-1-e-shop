package com.rychkov.eshop.controllers;

import com.rychkov.eshop.dtos.CartItem;
import com.rychkov.eshop.dtos.OrderAndBooks;
import com.rychkov.eshop.entitys.Book;
import com.rychkov.eshop.entitys.Order;
import com.rychkov.eshop.exceptions.FailedToRepeatOrderException;
import com.rychkov.eshop.repositorys.OrdersRepository;
import com.rychkov.eshop.repositorys.UserRepository;
import com.rychkov.eshop.services.OrderService;
import lombok.Getter;
import lombok.Setter;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.*;

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
    public JSONObject repeatOrder(@RequestBody Integer orderId, HttpSession session){
        JSONObject result = new JSONObject();
        List<CartItem> repeatCart;
        try{
            repeatCart = orderService.repeatCartFromOrder(orderId);
        } catch (FailedToRepeatOrderException e){
            result.put("error", true);
            result.put("message", e.getMessage());
            return result;
        }
        session.setAttribute("cart", repeatCart);
        result.put("error", false);
        result.put("message", "Chosen order was copied to your cart!");
        return result;
    }




}
