package com.rychkov.eshop.services;

import net.minidev.json.JSONObject;

import javax.servlet.http.HttpSession;

public interface CartService {
    JSONObject addItem(HttpSession session, JSONObject item);
    JSONObject deleteItem(HttpSession session, JSONObject item);
}
