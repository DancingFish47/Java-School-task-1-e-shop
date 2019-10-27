package com.rychkov.eshop.controllers;

import com.rychkov.eshop.entitys.Book;
import com.rychkov.eshop.exceptions.OutOfStockException;
import com.rychkov.eshop.repositorys.BookCategoryRepository;
import com.rychkov.eshop.repositorys.BooksRepository;
import com.rychkov.eshop.services.BookService;
import com.rychkov.eshop.services.CartService;
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


@Controller
public class BooksPageController {
    @Autowired
    private BooksRepository booksRepository;
    @Autowired
    private BookService bookService;
    @Autowired
    private BookCategoryRepository bookCategoryRepository;
    @Autowired
    private CartService cartService;


    @GetMapping({"/books/{category}", "/books"})
    public String showBooks(@PathVariable(required = false) String category, @RequestParam(value = "sort", required = false) String sortType, Model model) {
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
    public JSONObject addToCart(@RequestBody JSONObject addItem, Principal principal, HttpSession session) {
        JSONObject result = new JSONObject();
        try {
            result = cartService.addItem(session, addItem);
        } catch (OutOfStockException e) {
            result.put("error", true);
            result.put("message", e.getMessage());
            return result;
        }
        return result;
    }

}
