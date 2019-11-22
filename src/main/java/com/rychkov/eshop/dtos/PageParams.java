package com.rychkov.eshop.dtos;

import lombok.*;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
public class PageParams {
    private String category;
    private String sort;
    private Integer page;
}
