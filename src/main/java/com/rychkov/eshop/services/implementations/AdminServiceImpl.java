package com.rychkov.eshop.services.implementations;

import com.rychkov.eshop.dtos.TopBookDto;
import com.rychkov.eshop.dtos.TopUserDto;
import com.rychkov.eshop.entities.Book;
import com.rychkov.eshop.entities.Order;
import com.rychkov.eshop.entities.User;
import com.rychkov.eshop.repositories.BooksRepository;
import com.rychkov.eshop.repositories.OrdersRepository;
import com.rychkov.eshop.repositories.UserRepository;
import com.rychkov.eshop.services.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@RequiredArgsConstructor
@Service
public class AdminServiceImpl implements AdminService {
    private final BooksRepository booksRepository;
    private final OrdersRepository ordersRepository;
    private final UserRepository userRepository;

    @Override
    public List<TopBookDto> getTopBooksList() {
        List<TopBookDto> topBookDtos = new ArrayList<>();
        List<Book> allBooks = (List<Book>) booksRepository.findAll();
        for (Book book : allBooks) {
            topBookDtos.add(new TopBookDto(book.getName(), book.getSold(), book.getPrice() * book.getSold()));
        }
        topBookDtos.sort(Comparator.comparing(TopBookDto::getTotalValue).reversed());
        return topBookDtos;
    }

    @Override
    public List<TopUserDto> getTopUsersList() {
        List<TopUserDto> topUserDtos = new ArrayList<>();
        List<User> allUsers = (List<User>) userRepository.findAll();
        for (User user : allUsers) {
            TopUserDto topUser = new TopUserDto(user.getUsername(), (float) 0, 0);
            List<Order> orders = ordersRepository.findAllByUser(user);
            for (Order order : orders) {
                List<Book> books = order.getBooks();
                for (Book book : books) {
                    topUser.addBookCount(1);
                }
                topUser.addValue(order.getTotalPrice());
            }
            topUserDtos.add(topUser);
        }
        topUserDtos.sort(Comparator.comparing(TopUserDto::getTotalValue).reversed());
        return topUserDtos;
    }
}
