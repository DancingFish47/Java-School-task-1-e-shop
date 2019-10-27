package com.rychkov.eshop.dtos;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
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
