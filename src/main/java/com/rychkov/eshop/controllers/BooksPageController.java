package com.rychkov.eshop.controllers;

import com.rychkov.eshop.repositorys.BooksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/books")
public class BooksPageController {
    @Autowired
    private BooksRepository booksRepository;
    @GetMapping
    public String bookspage(Model model){
        model.addAttribute("books", booksRepository.findAll());
        return "books";
    }

}
