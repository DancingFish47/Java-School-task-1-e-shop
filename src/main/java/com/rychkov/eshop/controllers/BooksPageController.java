package com.rychkov.eshop.controllers;

import com.rychkov.eshop.entitys.Book;
import com.rychkov.eshop.entitys.Order;
import com.rychkov.eshop.exceptions.OutOfStockException;
import com.rychkov.eshop.exceptions.ReturnBooksToStockException;
import com.rychkov.eshop.repositorys.BookCategoryRepository;
import com.rychkov.eshop.repositorys.BooksRepository;
import com.rychkov.eshop.repositorys.OrdersRepository;
import com.rychkov.eshop.services.BookService;
import com.rychkov.eshop.services.CartService;
import com.rychkov.eshop.services.OrderService;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@Controller
public class BooksPageController {
    @Autowired
    private BookService bookService;
    @Autowired
    private BookCategoryRepository bookCategoryRepository;
    @Autowired
    private CartService cartService;
    @Autowired
    OrdersRepository ordersRepository;
    @Autowired
    OrderService orderService;


    @GetMapping({"/books/{category}", "/books"})
    public String showBooks(@PathVariable(required = false) String category,
                            @RequestParam(value = "sort", required = false) String sortType,
                            Model model,
                            HttpSession session) throws ReturnBooksToStockException {
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

        model.addAttribute("BookCategory", bookCategoryRepository.findAll());

        Map<String, String> params = new HashMap<>();
        params.put("category", category);
        params.put("sortType", sortType);

        List<Book> books = bookService.prepareBooksList(params);

        model.addAttribute("sort", sortType);
        model.addAttribute("category", category);
        model.addAttribute("books", books);
        return "books";
    }

    @RequestMapping(value = "/addToCart", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject addToCart(@RequestBody JSONObject addItem, HttpSession session) throws OutOfStockException {
        return cartService.addItem(session, addItem);
    }

}
