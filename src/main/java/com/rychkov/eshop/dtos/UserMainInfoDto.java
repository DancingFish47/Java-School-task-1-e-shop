package com.rychkov.eshop.dtos;

import lombok.*;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
public class UserMainInfoDto {
    private String firstname;
    private String lastname;
    private String birthdate;
}
