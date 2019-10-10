package com.rychkov.eshop.Controllers;

import com.rychkov.eshop.Entitys.Users;
import com.rychkov.eshop.Entitys.Orders;
import com.rychkov.eshop.Repositorys.CustomerRepository;
import com.rychkov.eshop.Repositorys.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TempControllers {

    @Autowired
    private CustomerRepository customerRepository;
    @GetMapping("/temp")
    public @ResponseBody
    Iterable<Users> getAllCustomers() {
        return customerRepository.findAll();
    }

    @Autowired
    private OrderRepository orderRepository;
    @GetMapping("/tempOrders")
    public @ResponseBody
    Iterable<Orders> getAllOrders() {
        return orderRepository.findAll();
    }


}
