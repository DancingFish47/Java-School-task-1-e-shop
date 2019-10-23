package com.rychkov.eshop.dtos;

import com.rychkov.eshop.entitys.User;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
@Getter
@Setter
public class OrderInfoDto {
    private Integer creditCardDateYear;
    private Integer creditCardCvv;
    private String deliveryMethod;
    private String cardOwner;
    private String paymentMethod;
    private String cardNumber;
    private Integer addressId;
    private Integer creditCardDateMonth;
    private List<CartItem> cartItems;
    private User user;
}
