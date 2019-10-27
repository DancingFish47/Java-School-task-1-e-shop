package com.rychkov.eshop.controllers;

import com.rychkov.eshop.dtos.BookDto;
import com.rychkov.eshop.dtos.CartItem;
import com.rychkov.eshop.dtos.NewStatusDto;
import com.rychkov.eshop.dtos.OrderAndBooks;
import com.rychkov.eshop.entitys.Book;
import com.rychkov.eshop.entitys.BookCategory;
import com.rychkov.eshop.entitys.Order;
import com.rychkov.eshop.exceptions.BookException;
import com.rychkov.eshop.exceptions.FailedToChangeStatusException;
import com.rychkov.eshop.exceptions.GenreException;
import com.rychkov.eshop.repositorys.*;
import com.rychkov.eshop.services.*;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class AdminPageController {
    @Autowired
    BooksRepository booksRepository;
    @Autowired
    BookCategoryRepository bookCategoryRepository;
    @Autowired
    AdminService adminService;
    @Autowired
    BookService bookService;
    @Autowired
    OrderService orderService;
    @Autowired
    UserService userService;
    @Autowired
    OrderStatusRepository orderStatusRepository;
    @Autowired
    PaymentStatusRepository paymentStatusRepository;
    @Autowired
    GenreService genreService;

    @GetMapping(value = "/adminPage")
    public String adminPage() {
        return "adminOrders";
    }

    @GetMapping("/adminPage/adminOrders")
    public String adminOrdersView(Model model){
        model.addAttribute("ordersAndBooks", orderService.findAllOrders());
        model.addAttribute("orderStatus", orderStatusRepository.findAll());
        model.addAttribute("paymentStatus", paymentStatusRepository.findAll());
        return "adminOrders";
    }

    @GetMapping("/adminPage/adminStats")
    public String adminStatsView(Model model){
        model.addAttribute("topBooks", adminService.getTopBooksList());
        model.addAttribute("topUsers", adminService.getTopUsersList());
        return "adminStats";
    }

    @GetMapping("/adminPage/adminManageBooks")
    public String newBookView(Model model){
        model.addAttribute("allBooks", booksRepository.findAll());
        model.addAttribute("genres", bookCategoryRepository.findAll());
        return "adminManageBooks";
    }

    @GetMapping("/adminPage/adminManageCategories")
    public String newCategoryView(Model model){
        model.addAttribute("genres", bookCategoryRepository.findAll());
        return "adminManageCategories";
    }

    @RequestMapping(value = "/adminPage/changeOrderStatus", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject changeOrderStatus(@RequestBody NewStatusDto newStatusDto){
        JSONObject result = new JSONObject();
        try {
            orderService.changeOrderStatus(newStatusDto);
        } catch (FailedToChangeStatusException e) {
            result.put("error", true);
            result.put("message", e.getMessage());
            return result;
        }
        result.put("error", false);
        result.put("message", "Order status for order with id " + newStatusDto.getOrderId() + " changed!");
        return result;
    }

    @RequestMapping(value = "/adminPage/changePaymentStatus", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject changePaymentStatus(@RequestBody NewStatusDto newStatusDto) {
        JSONObject result = new JSONObject();
        try {
            orderService.changePaymentStatus(newStatusDto);
        } catch (FailedToChangeStatusException e) {
            result.put("error", true);
            result.put("message", e.getMessage());
            return result;
        }
        result.put("error", false);
        result.put("message", "Payment status for order with id " + newStatusDto.getOrderId() + " changed!");
        return result;
    }

    @RequestMapping(value = "adminPage/adminManageCategories/getGenreById", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject getGenreById(@RequestBody Integer genreId){
        JSONObject result = new JSONObject();
        result.put("genre", bookCategoryRepository.findById(genreId));
        return result;
    }

    @RequestMapping(value = "adminPage/adminManageCategories/deleteGenre", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject deleteGenreById(@RequestBody Integer deleteId){
        JSONObject result = new JSONObject();
        try {
            genreService.deleteGenre(deleteId);
        } catch (GenreException e) {
            result.put("error", true);
            result.put("message", e.getMessage());
            return result;
        }
        result.put("error", false);
        result.put("message", "Genre deleted!");
        return result;
    }


    @RequestMapping(value = "adminPage/adminManageCategories/saveEditGenre", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject saveEditGenre(@RequestBody JSONObject edit){
        JSONObject result = new JSONObject();
        try {
            genreService.editGenre((Integer) edit.get("id"), (String) edit.get("genre"));
        } catch (GenreException e) {
            result.put("error", true);
            result.put("message", e.getMessage());
            return result;
        }
        result.put("error", false);
        result.put("message", "Genre edited!");
        return result;
    }

    @RequestMapping(value = "adminPage/adminManageCategories/saveNewBook", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject saveNewBook(@RequestBody BookDto bookDto){
        JSONObject result = new JSONObject();
        try{
            bookService.addNewBook(bookDto);
        } catch (BookException e){
            result.put("error", true);
            result.put("message", e.getMessage());
            return result;
        }
        result.put("error", false);
        result.put("message", "New book " + bookDto.getName() + " added!");
        return result;
    }

    @RequestMapping(value = "adminPage/adminManageCategories/saveEditBook", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject saveEditBook(@RequestBody BookDto bookDto){
        JSONObject result = new JSONObject();
        try{
            bookService.editBook(bookDto);
        } catch (BookException e){
            result.put("error", true);
            result.put("message", e.getMessage());
            return result;
        }
        result.put("error", false);
        result.put("message", "Book details for " + bookDto.getName() + " saved!");
        return result;
    }

    @RequestMapping(value = "adminPage/adminManageCategories/getBookById", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject getBookById(@RequestBody Integer bookId){
        JSONObject result = new JSONObject();
        result.put("book", booksRepository.findById(bookId));
        return result;
    }

    @RequestMapping(value = "adminPage/adminManageCategories/deleteBook", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject deleteBookById(@RequestBody Integer deleteId){
        JSONObject result = new JSONObject();
        try{
            bookService.deleteBookById(deleteId);
        } catch (BookException e){
            result.put("error", true);
            result.put("message", e.getMessage());
            return result;
        }
        result.put("error", false);
        result.put("message", "Book deleted!");
        return result;
    }
}
