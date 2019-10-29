package com.rychkov.eshop.dtos;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
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
