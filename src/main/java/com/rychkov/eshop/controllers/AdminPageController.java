package com.rychkov.eshop.controllers;

import com.rychkov.eshop.dtos.CartItem;
import com.rychkov.eshop.dtos.NewStatusDto;
import com.rychkov.eshop.dtos.OrderAndBooks;
import com.rychkov.eshop.entitys.Book;
import com.rychkov.eshop.entitys.Order;
import com.rychkov.eshop.exceptions.FailedToChangeStatusException;
import com.rychkov.eshop.repositorys.OrderStatusRepository;
import com.rychkov.eshop.repositorys.OrdersRepository;
import com.rychkov.eshop.repositorys.PaymentStatusRepository;
import com.rychkov.eshop.services.AdminService;
import com.rychkov.eshop.services.BookService;
import com.rychkov.eshop.services.OrderService;
import com.rychkov.eshop.services.UserService;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/adminPage")
public class AdminPageController {
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

    @GetMapping
    public String adminPage() {
        return "adminOrders";
    }

    @GetMapping("/adminOrders")
    public String adminOrdersView(Model model){
        model.addAttribute("ordersAndBooks", orderService.findAllOrders());
        model.addAttribute("orderStatus", orderStatusRepository.findAll());
        model.addAttribute("paymentStatus", paymentStatusRepository.findAll());
        return "adminOrders";
    }

    @GetMapping("/adminStats")
    public String adminStatsView(Model model){
        model.addAttribute("topBooks", adminService.getTopBooksList());
        model.addAttribute("topUsers", adminService.getTopUsersList());
        return "adminStats";
    }

    @GetMapping("/adminNewBook")
    public String newBookView(){
        return "adminNewBook";
    }

    @GetMapping("/adminNewCategory")
    public String newCategoryView(){
        return "adminNewCategory";
    }

    @RequestMapping(value = "/changeOrderStatus")
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

    @RequestMapping(value = "/changePaymentStatus")
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

}
