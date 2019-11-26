package com.rychkov.eshop.dtos;

import com.rychkov.eshop.entities.Address;
import com.rychkov.eshop.entities.Book;
import com.rychkov.eshop.entities.BookCategory;
import com.rychkov.eshop.entities.User;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResponseDto {
    private String message;
    private BookCategory genre;
    private Book book;
    private boolean error;
    private User user;
    private UserMainInfoDto userMainInfoDto;
    private Address address;
}
