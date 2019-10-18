package com.rychkov.eshop.controllers;

import com.rychkov.eshop.entitys.Book;
import com.rychkov.eshop.repositorys.BooksRepository;
import com.rychkov.eshop.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.List;


@Controller
public class BooksPageController {
    @Autowired
    private BooksRepository booksRepository;
    @Autowired
    private BookService bookService;

    @RequestMapping(value = "/books")
    public ModelAndView bookspage(Model model, Principal principal){
        return new ModelAndView("books","books", booksRepository.findAll());
    }

    @RequestMapping(value = "/books/sortByName")
    public ModelAndView booksSortedByName(Model model){
        List<Book> books;
        books = bookService.sortByName();
        model.addAttribute("books", books);
        return new ModelAndView("books", "books", books);
    }
    @RequestMapping(value = "/books/sortByPrice")
    public ModelAndView booksSortedByPrice(Model model){
        List<Book> books;
        books = bookService.sortByPrice();
        model.addAttribute("books", books);
        return new ModelAndView("books", "books", books);
    }

    @RequestMapping(value = "books/addToCart", method = RequestMethod.POST)
    @ResponseBody
    public String addToCart(
            @RequestParam("bookId") long id) {
        return "BookId" + id;
    }

}
