package com.rychkov.eshop.controllers;


import com.rychkov.eshop.dtos.CartItem;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.List;


@Controller
public class CartPageController {
    @GetMapping("/cart")
    public String cartpage(HttpSession session, Model model){
        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
        float total = 0;
        model.addAttribute("cart", cart);
        if(cart!=null){
            for (CartItem cartItem : cart){
                total+= cartItem.getBook().getPrice() * cartItem.getQuantity();
            }
            model.addAttribute("total", total);
        }
        return "cart";
    }
}
