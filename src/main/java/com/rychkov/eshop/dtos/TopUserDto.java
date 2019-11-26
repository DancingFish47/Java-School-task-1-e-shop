package com.rychkov.eshop.dtos;

import lombok.Data;

@Data
public class TopUserDto {
    private String name;
    private Float totalValue;
    private Integer totalBooks;

    public TopUserDto(String name, Float totalValue, Integer totalBooks) {
        this.name = name;
        this.totalBooks = totalBooks;
        this.totalValue = totalValue;
    }

    public void addValue(Float value) {
        this.totalValue += value;
    }

    public void addBookCount(Integer count) {
        this.totalBooks += count;
    }
}
