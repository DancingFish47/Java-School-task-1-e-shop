package com.rychkov.eshop.dtos;

import com.rychkov.eshop.entities.User;
import lombok.*;

import java.util.List;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
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
