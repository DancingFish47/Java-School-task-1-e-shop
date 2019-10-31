package com.rychkov.eshop.controllers;


import com.rychkov.eshop.dtos.CartItem;
import com.rychkov.eshop.entitys.Order;
import com.rychkov.eshop.exceptions.OutOfStockException;
import com.rychkov.eshop.exceptions.ProcessOrderException;
import com.rychkov.eshop.exceptions.ReturnBooksToStockException;
import com.rychkov.eshop.repositorys.OrdersRepository;
import com.rychkov.eshop.services.CartService;
import com.rychkov.eshop.services.OrderService;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;


@Controller
public class CartPageController {
    @Autowired
    CartService cartService;
    @Autowired
    OrdersRepository ordersRepository;
    @Autowired
    OrderService orderService;

    @GetMapping("/cart")
    public String cartpage(HttpSession session, Model model) throws ReturnBooksToStockException {
        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
        if(session.getAttribute("orderId") != null){
            Integer orderId = (Integer) session.getAttribute("orderId");
            Optional<Order> optionalOrder = ordersRepository.findById(orderId);
            if (optionalOrder.isPresent()){
                Order order = optionalOrder.get();
                orderService.returnBooks(order);
                ordersRepository.delete(order);
                session.setAttribute("orderId", null);
            }
        }
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

    @RequestMapping(value = "/checkStocks", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject checkStocks(HttpSession session) throws ProcessOrderException, OutOfStockException {
        cartService.checkStocksAndCreateTempOrder(session);
        return new JSONObject();
    }
}
