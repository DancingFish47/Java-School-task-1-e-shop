package com.rychkov.eshop.services;

import com.rychkov.eshop.dtos.TopBookDto;
import com.rychkov.eshop.dtos.TopUserDto;

import java.util.List;

public interface AdminService {
    List<TopBookDto> getTopBooksList();
    List<TopUserDto> getTopUsersList();
}
