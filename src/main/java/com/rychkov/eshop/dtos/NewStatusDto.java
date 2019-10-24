package com.rychkov.eshop.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NewStatusDto {
    private Integer orderId;
    private String newStatusName;
}
