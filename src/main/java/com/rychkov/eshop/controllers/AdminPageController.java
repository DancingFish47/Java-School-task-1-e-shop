package com.rychkov.eshop.controllers;

import com.rychkov.eshop.dtos.BookDto;
import com.rychkov.eshop.dtos.NewStatusDto;
import com.rychkov.eshop.dtos.ResponseDto;
import com.rychkov.eshop.entities.Book;
import com.rychkov.eshop.entities.BookCategory;
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

import java.util.Optional;

@RequiredArgsConstructor
@Controller
public class AdminPageController {
    private final BooksRepository booksRepository;
    private final BookCategoryRepository bookCategoryRepository;
    private final AdminService adminService;
    private final BookService bookService;
    private final OrderService orderService;
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
    public String adminManageBooksView(Model model) {
        model.addAttribute("allBooks", booksRepository.findAll());
        model.addAttribute("genres", bookCategoryRepository.findAll());
        return "adminManageBooks";
    }

    @GetMapping("/adminPage/adminManageCategories")
    public String adminManageCategoriesView(Model model) {
        model.addAttribute("genres", bookCategoryRepository.findAll());
        return "adminManageCategories";
    }

    @RequestMapping(value = "/adminPage/changeOrderStatus", method = RequestMethod.POST)
    @ResponseBody
    public ResponseDto changeOrderStatus(@RequestBody NewStatusDto newStatusDto) throws FailedToChangeStatusException {
        orderService.changeOrderStatus(newStatusDto);
        return ResponseDto.builder()
                .message("Order status for order with id " + newStatusDto.getOrderId() + " changed!")
                .build();
    }

    @RequestMapping(value = "/adminPage/changePaymentStatus", method = RequestMethod.POST)
    @ResponseBody
    public ResponseDto changePaymentStatus(@RequestBody NewStatusDto newStatusDto) throws FailedToChangeStatusException {
        orderService.changePaymentStatus(newStatusDto);
        return ResponseDto.builder()
                .message("Payment status for order with id " + newStatusDto.getOrderId() + " changed!")
                .build();
    }

    @RequestMapping(value = "adminPage/adminManageCategories/getGenreById", method = RequestMethod.POST)
    @ResponseBody
    public ResponseDto getGenreById(@RequestBody Integer genreId) throws GenreException{
        Optional<BookCategory> genreOptional = bookCategoryRepository.findById(genreId);
        if (!genreOptional.isPresent()) throw new GenreException("Genre not found");
        return ResponseDto.builder()
                .genre(genreOptional.get())
                .build();
    }

    @RequestMapping(value = "adminPage/adminManageCategories/deleteGenre", method = RequestMethod.POST)
    @ResponseBody
    public ResponseDto deleteGenreById(@RequestBody Integer deleteId) throws GenreException {
        genreService.deleteGenre(deleteId);
        return ResponseDto.builder()
                .message("Genre deleted!")
                .build();
    }


    @RequestMapping(value = "adminPage/adminManageCategories/saveEditGenre", method = RequestMethod.POST)
    @ResponseBody
    public ResponseDto saveEditGenre(@RequestBody JSONObject edit) throws GenreException {
        genreService.editGenre((Integer) edit.get("id"), (String) edit.get("genre"));
        return ResponseDto.builder()
                .message("Genre edited!")
                .build();
    }

    @RequestMapping(value = "adminPage/adminManageCategories/saveNewGenre", method = RequestMethod.POST)
    @ResponseBody
    public ResponseDto saveNewGenre(@RequestBody String genre) throws GenreException {
        genreService.addGenre(genre);
        return ResponseDto.builder()
                .message("Genre saved!")
                .build();
    }

    @RequestMapping(value = "adminPage/adminManageBooks/saveNewBook", method = RequestMethod.POST)
    @ResponseBody
    public ResponseDto saveNewBook(@RequestBody BookDto bookDto) throws BookException {
        bookService.addNewBook(bookDto);
        return ResponseDto.builder()
                .message("New book " + bookDto.getName() + " added!")
                .build();
    }

    @RequestMapping(value = "adminPage/adminManageBooks/saveEditBook", method = RequestMethod.POST)
    @ResponseBody
    public ResponseDto saveEditBook(@RequestBody BookDto bookDto) throws BookException {
        bookService.editBook(bookDto);
        return ResponseDto.builder()
                .message("Book details for"  + bookDto.getName() +  " saved!")
                .build();
    }

    @RequestMapping(value = "adminPage/adminManageBooks/getBookById", method = RequestMethod.POST)
    @ResponseBody
    public ResponseDto getBookById(@RequestBody Integer bookId) throws BookException{
        Optional<Book> optionalBook = booksRepository.findById(bookId);
        if(!optionalBook.isPresent()) throw new BookException("Book not found");
        return ResponseDto.builder()
                .book(optionalBook.get())
                .build();
    }

    @RequestMapping(value = "adminPage/adminManageBooks/deleteBook", method = RequestMethod.POST)
    @ResponseBody
    public ResponseDto deleteBookById(@RequestBody Integer deleteId) throws BookException {
        bookService.deleteBookById(deleteId);
        return ResponseDto.builder()
                .message("Book deleted!")
                .build();
    }
}
