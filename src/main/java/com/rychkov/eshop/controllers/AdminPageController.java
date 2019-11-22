package com.rychkov.eshop.controllers;

import com.rychkov.eshop.dtos.BookDto;
import com.rychkov.eshop.dtos.NewStatusDto;
import com.rychkov.eshop.exceptions.BookException;
import com.rychkov.eshop.exceptions.FailedToChangeStatusException;
import com.rychkov.eshop.exceptions.GenreException;
import com.rychkov.eshop.repositories.BookCategoryRepository;
import com.rychkov.eshop.repositories.BooksRepository;
import com.rychkov.eshop.repositories.OrderStatusRepository;
import com.rychkov.eshop.repositories.PaymentStatusRepository;
import com.rychkov.eshop.services.*;
import lombok.RequiredArgsConstructor;
import net.minidev.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@Controller
public class AdminPageController {
    private final BooksRepository booksRepository;
    private final BookCategoryRepository bookCategoryRepository;
    private final AdminService adminService;
    private final BookService bookService;
    private final OrderService orderService;
    private final UserService userService;
    private final OrderStatusRepository orderStatusRepository;
    private final PaymentStatusRepository paymentStatusRepository;
    private final GenreService genreService;

    @GetMapping(value = "/adminPage")
    public String adminPage() {
        return "adminOrders";
    }

    @GetMapping("/adminPage/adminOrders")
    public String adminOrdersView(Model model) {
        model.addAttribute("ordersAndBooks", orderService.findAllOrders());
        model.addAttribute("orderStatus", orderStatusRepository.findAll());
        model.addAttribute("paymentStatus", paymentStatusRepository.findAll());
        return "adminOrders";
    }

    @GetMapping("/adminPage/adminStats")
    public String adminStatsView(Model model) {
        model.addAttribute("topBooks", adminService.getTopBooksList());
        model.addAttribute("topUsers", adminService.getTopUsersList());
        return "adminStats";
    }

    @GetMapping("/adminPage/adminManageBooks")
    public String newBookView(Model model) {
        model.addAttribute("allBooks", booksRepository.findAll());
        model.addAttribute("genres", bookCategoryRepository.findAll());
        return "adminManageBooks";
    }

    @GetMapping("/adminPage/adminManageCategories")
    public String newCategoryView(Model model) {
        model.addAttribute("genres", bookCategoryRepository.findAll());
        return "adminManageCategories";
    }

    @RequestMapping(value = "/adminPage/changeOrderStatus", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject changeOrderStatus(@RequestBody NewStatusDto newStatusDto) throws FailedToChangeStatusException {
        JSONObject result = new JSONObject();
        orderService.changeOrderStatus(newStatusDto);
        result.put("message", "Order status for order with id " + newStatusDto.getOrderId() + " changed!");
        return result;
    }

    @RequestMapping(value = "/adminPage/changePaymentStatus", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject changePaymentStatus(@RequestBody NewStatusDto newStatusDto) throws FailedToChangeStatusException {
        JSONObject result = new JSONObject();
        orderService.changePaymentStatus(newStatusDto);
        result.put("message", "Payment status for order with id " + newStatusDto.getOrderId() + " changed!");
        return result;
    }

    @RequestMapping(value = "adminPage/adminManageCategories/getGenreById", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject getGenreById(@RequestBody Integer genreId) {
        JSONObject result = new JSONObject();
        result.put("genre", bookCategoryRepository.findById(genreId));
        return result;
    }

    @RequestMapping(value = "adminPage/adminManageCategories/deleteGenre", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject deleteGenreById(@RequestBody Integer deleteId) throws GenreException {
        JSONObject result = new JSONObject();
        genreService.deleteGenre(deleteId);
        result.put("message", "Genre deleted!");
        return result;
    }


    @RequestMapping(value = "adminPage/adminManageCategories/saveEditGenre", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject saveEditGenre(@RequestBody JSONObject edit) throws GenreException {
        JSONObject result = new JSONObject();
        genreService.editGenre((Integer) edit.get("id"), (String) edit.get("genre"));
        result.put("message", "Genre edited!");
        return result;
    }

    @RequestMapping(value = "adminPage/adminManageCategories/saveNewGenre", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject saveNewGenre(@RequestBody String genre) throws GenreException {
        JSONObject result = new JSONObject();
        genreService.addGenre(genre);
        result.put("message", "Genre edited!");
        return result;
    }

    @RequestMapping(value = "adminPage/adminManageCategories/saveNewBook", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject saveNewBook(@RequestBody BookDto bookDto) throws BookException {
        JSONObject result = new JSONObject();
        bookService.addNewBook(bookDto);
        result.put("message", "New book " + bookDto.getName() + " added!");
        return result;
    }

    @RequestMapping(value = "adminPage/adminManageCategories/saveEditBook", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject saveEditBook(@RequestBody BookDto bookDto) throws BookException {
        JSONObject result = new JSONObject();
        bookService.editBook(bookDto);
        result.put("message", "Book details for " + bookDto.getName() + " saved!");
        return result;
    }

    @RequestMapping(value = "adminPage/adminManageCategories/getBookById", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject getBookById(@RequestBody Integer bookId) {
        JSONObject result = new JSONObject();
        result.put("book", booksRepository.findById(bookId));
        return result;
    }

    @RequestMapping(value = "adminPage/adminManageCategories/deleteBook", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject deleteBookById(@RequestBody Integer deleteId) throws BookException {
        JSONObject result = new JSONObject();
        bookService.deleteBookById(deleteId);
        result.put("message", "Book deleted!");
        return result;
    }
}
