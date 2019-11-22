package com.rychkov.eshop.dtos;

import lombok.*;

@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Data
public class AddressDto {
    private Integer id;
    private String country;
    private String city;
    private String street;
    private String building;
    private String apartment;
    private String zip;
}
