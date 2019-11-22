package com.rychkov.eshop.dtos;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class TopBookDto {
    private String name;
    private Integer sold;
    private Float totalValue;

    public TopBookDto(String name, Integer sold, Float totalValue) {
        this.name = name;
        this.sold = sold;
        this.totalValue = totalValue;
    }
}
