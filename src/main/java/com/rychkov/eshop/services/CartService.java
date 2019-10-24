package com.rychkov.eshop.services;

import com.rychkov.eshop.dtos.CartItem;
import com.rychkov.eshop.exceptions.OutOfStockException;
import net.minidev.json.JSONObject;

import javax.servlet.http.HttpSession;
import java.util.List;

public interface CartService {
    JSONObject addItem(HttpSession session, JSONObject item);
    JSONObject deleteItem(HttpSession session, JSONObject item);
    void checkStocks(List<CartItem> cart) throws OutOfStockException;
}
