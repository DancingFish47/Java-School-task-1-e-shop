package com.rychkov.eshop.controllers;


import com.rychkov.eshop.dtos.Cart;
import com.rychkov.eshop.dtos.CartItem;
import com.rychkov.eshop.entities.Order;
import com.rychkov.eshop.exceptions.BookException;
import com.rychkov.eshop.exceptions.OutOfStockException;
import com.rychkov.eshop.exceptions.ProcessOrderException;
import com.rychkov.eshop.exceptions.ReturnBooksToStockException;
import com.rychkov.eshop.repositories.OrdersRepository;
import com.rychkov.eshop.services.CartService;
import com.rychkov.eshop.services.OrderService;
import lombok.RequiredArgsConstructor;
import net.minidev.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;


@Controller
@RequiredArgsConstructor
public class CartPageController {
    private final CartService cartService;
    private final OrdersRepository ordersRepository;
    private final OrderService orderService;

    @GetMapping("/cart")
    public String cartpage(HttpSession session, Model model) throws ReturnBooksToStockException {
        Cart cart = (Cart) session.getAttribute("cart");



        if (session.getAttribute("orderId") != null) {
            Integer orderId = (Integer) session.getAttribute("orderId");
            Optional<Order> optionalOrder = ordersRepository.findById(orderId);
            if (optionalOrder.isPresent()) {
                Order order = optionalOrder.get();
                orderService.returnBooks(order);
                ordersRepository.delete(order);
                session.setAttribute("orderId", null);
            }
        }

        float total = 0;
        if(cart != null){
            model.addAttribute("cart", cart.getCartItems());
            if (cart.getCartItems() != null) {
                for (CartItem cartItem : cart.getCartItems()) {
                    total += cartItem.getBook().getPrice() * cartItem.getQuantity();
                }
                model.addAttribute("total", total);
            }
        }

        return "cart";
    }

    @RequestMapping(value = "/deleteFromCart", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject deleteFromCart(@RequestBody Integer deleteId, HttpSession session) throws BookException {
        JSONObject result = new JSONObject();
        Cart cart = (Cart) session.getAttribute("cart");
        if(cart == null){
            cart = new Cart();
            session.setAttribute("cart", cart);
        }
        cartService.deleteItem(cart, deleteId);
        result.put("error", false);
        result.put("message", "Book's deleted from your cart!");
        return result;
    }

    @RequestMapping(value = "/checkStocks", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject checkStocks(HttpSession session) throws ProcessOrderException, OutOfStockException {
        Cart cart = (Cart) session.getAttribute("cart");
        if(cart == null){
            cart = new Cart();
            session.setAttribute("cart", cart);
        }
        int orderId = cartService.checkStocksAndCreateTempOrder(cart);
        session.setAttribute("orderId", orderId);
        return new JSONObject();
    }
}
