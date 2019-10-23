package com.rychkov.eshop.controllers;


import com.rychkov.eshop.dtos.CartItem;
import com.rychkov.eshop.services.CartService;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;


@Controller
public class CartPageController {
    @Autowired
    CartService cartService;

    @GetMapping("/cart")
    public String cartpage(HttpSession session, Model model) {
        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
        float total = 0;
        model.addAttribute("cart", cart);
        if (cart != null) {
            for (CartItem cartItem : cart) {
                total += cartItem.getBook().getPrice() * cartItem.getQuantity();
            }
            model.addAttribute("total", total);
        }
        return "cart";
    }

    @RequestMapping(value = "/deleteFromCart", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject deleteFromCart(@RequestBody JSONObject deleteId, HttpSession session) {
        JSONObject result;
        result = cartService.deleteItem(session, deleteId);
        return result;
    }
}
