package com.rychkov.eshop.dtos;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class NewStatusDto {
    private Integer orderId;
    private String newStatusName;
}
