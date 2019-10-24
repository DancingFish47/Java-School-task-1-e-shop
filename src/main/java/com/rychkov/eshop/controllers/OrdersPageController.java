package com.rychkov.eshop.controllers;

import com.rychkov.eshop.dtos.CartItem;
import com.rychkov.eshop.entitys.Book;
import com.rychkov.eshop.entitys.Order;
import com.rychkov.eshop.repositorys.OrdersRepository;
import com.rychkov.eshop.repositorys.UserRepository;
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
    OrdersRepository ordersRepository;
    @Autowired
    UserRepository userRepository;

    @RequestMapping(value = "/orders", method = RequestMethod.GET)
    public String ordersView(Principal principal, Model model) {
        List<OrderAndBooks> orderAndBooks = new ArrayList<>();
        List<Order> orderList = ordersRepository.findAllByUser(userRepository.findByUsername(principal.getName()));

        for (Order order : orderList){
            List<CartItem> bookList = new ArrayList<>();
            for(Book book : order.getBooks()) {
                if (exists(book.getId(), bookList) == -1){
                    bookList.add(new CartItem(book, 1));
                } else {
                    bookList.get(exists(book.getId(), bookList)).addQuantity(1);
                }
            }
            orderAndBooks.add(new OrderAndBooks(order, bookList));
        }
        model.addAttribute("orderAndBooks", orderAndBooks);
        return "orders";
    }

    @RequestMapping(value = "/repeatOrder", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject repeatOrder(@RequestBody Integer orderId, Principal principal, HttpSession session){
        JSONObject result = new JSONObject();

        List<CartItem> repeatCart = new ArrayList<>();
        Optional<Order> optionalOrder = ordersRepository.findById(orderId);

        if(optionalOrder.isPresent()){
            Order order = optionalOrder.get();
            for(Book book : order.getBooks()) {
                if (exists(book.getId(), repeatCart) == -1){
                    repeatCart.add(new CartItem(book, 1));
                } else {
                    repeatCart.get(exists(book.getId(), repeatCart)).addQuantity(1);
                }
            }
        }else{
            result.put("error", true);
            result.put("message", "Can not find order with given id!");
            return result;
        }

        session.setAttribute("cart", repeatCart);
        result.put("error", false);
        result.put("message", "Chosen order was copied to your cart!");
        return result;
    }

    private int exists(Integer id, List<CartItem> cart) {
        for (int i = 0; i < cart.size(); i++) {
            if (cart.get(i).getBook().getId().equals(id)) {
                return i;
            }
        }
        return -1;
    }

    @Getter
    @Setter
    private static class OrderAndBooks{
        private Order order;
        private List<CartItem> books;

        OrderAndBooks(Order order, List<CartItem> books){
            this.order = order;
            this.books = books;
        }
    }
}
