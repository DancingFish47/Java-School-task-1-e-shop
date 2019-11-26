package com.rychkov.eshop.dtos;

import lombok.Data;


@Data
public class BookDto {
    private String name;
    private String author;
    private Integer amount;
    private Float price;
    private Integer pages;
    private String genre;
    private Integer sold;
    private Integer id;
}
