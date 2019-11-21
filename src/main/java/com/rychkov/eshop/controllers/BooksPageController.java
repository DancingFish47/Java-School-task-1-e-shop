package com.rychkov.eshop.controllers;

import com.rychkov.eshop.dtos.AddItemDto;
import com.rychkov.eshop.entitys.Book;
import com.rychkov.eshop.entitys.Order;
import com.rychkov.eshop.exceptions.OutOfStockException;
import com.rychkov.eshop.exceptions.ReturnBooksToStockException;
import com.rychkov.eshop.repositorys.BookCategoryRepository;
import com.rychkov.eshop.repositorys.OrdersRepository;
import com.rychkov.eshop.services.BookService;
import com.rychkov.eshop.services.CartService;
import com.rychkov.eshop.services.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONObject;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
@Controller
public class BooksPageController {
    private final BookService bookService;
    private final BookCategoryRepository bookCategoryRepository;
    private final CartService cartService;
    private final OrdersRepository ordersRepository;
    private final OrderService orderService;


    @GetMapping({"/books/{category}", "/books"})
    public String showBooks(@PathVariable(required = false) String category,
                            @RequestParam(value = "sort", required = false) String sortType,
                            @RequestParam(value = "page", required = false, defaultValue = "1") String page,
                            Model model,
                            HttpSession session) throws ReturnBooksToStockException {

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

        Map<String, String> params = new HashMap<>();
        params.put("category", category);
        params.put("sortType", sortType);
        params.put("page", page);

        Page<Book> books = bookService.prepareBooksList(params);

        int maxpage = books.getTotalPages();
        int nextpage = 0;
        int prevpage = 0;

        if (Integer.parseInt(page) + 1 <= maxpage) nextpage = Integer.parseInt(page) + 1;
        if (Integer.parseInt(page) - 1 > 0) prevpage = Integer.parseInt(page) - 1;

        model.addAttribute("BookCategory", bookCategoryRepository.findAll());
        model.addAttribute("sort", sortType);
        model.addAttribute("category", category);
        model.addAttribute("books", books);
        model.addAttribute("page", page);
        model.addAttribute("nextpage", nextpage);
        model.addAttribute("prevpage", prevpage);
        return "books";
    }

    @RequestMapping(value = "/addToCart", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject addToCart(@RequestBody AddItemDto addItem, HttpSession session) throws OutOfStockException {
        return cartService.addItem(session, addItem);
    }

}
