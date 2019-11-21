package com.rychkov.eshop.services;

import com.rychkov.eshop.dtos.AddItemDto;
import com.rychkov.eshop.exceptions.OutOfStockException;
import com.rychkov.eshop.exceptions.ProcessOrderException;
import net.minidev.json.JSONObject;

import javax.servlet.http.HttpSession;

public interface CartService {
    JSONObject addItem(HttpSession session, AddItemDto item) throws OutOfStockException;

    JSONObject deleteItem(HttpSession session, Integer item);

    void checkStocksAndCreateTempOrder(HttpSession session) throws OutOfStockException, ProcessOrderException;
}
