package com.rychkov.eshop.dtos;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class AddItemDto {
    private int id;
    private int quantity;
}
