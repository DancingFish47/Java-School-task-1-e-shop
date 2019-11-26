package com.rychkov.eshop.dtos;

import lombok.Data;

@Data
public class NewStatusDto {
    private Integer orderId;
    private String newStatusName;
}
