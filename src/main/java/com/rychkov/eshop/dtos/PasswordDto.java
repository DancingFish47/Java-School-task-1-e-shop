package com.rychkov.eshop.dtos;

import lombok.*;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
public class PasswordDto {
    private String currentPassword;
    private String newPassword;
}
