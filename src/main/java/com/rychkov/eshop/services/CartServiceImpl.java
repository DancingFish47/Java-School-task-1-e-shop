package com.rychkov.eshop.services;

import com.rychkov.eshop.dtos.CartItem;
import com.rychkov.eshop.entitys.Book;
import com.rychkov.eshop.repositorys.BooksRepository;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CartServiceImpl implements CartService {
    @Autowired
    BooksRepository booksRepository;
    @Override
    public JSONObject addItem(HttpSession session, JSONObject item) {
        JSONObject result = new JSONObject();
        Integer addBookId = Integer.valueOf((String) item.get("id"));
        Integer quantity = Integer.valueOf((String) item.get("quantity"));
        Optional<Book> optionalBook = booksRepository.findById(addBookId);
        if(optionalBook.isPresent()) {
            Book book = optionalBook.get();
            CartItem cartItem = new CartItem(book, quantity);
            if (session.getAttribute("cart") == null) {
                List<CartItem> cart = new ArrayList<>();
                cart.add(cartItem);
                session.setAttribute("cart", cart);
            } else {
                List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
                if (exists(addBookId, cart) == -1) {
                    cart.add(cartItem);
                } else {
                    cart.get(exists(addBookId,cart)).addQuantity(quantity);
                }
                session.setAttribute("cart", cart);
            }
            result.put("error", false);
            result.put("message", "Book " + book.getName() + " added to cart with quantity " + quantity);
        } else {
            result.put("error", true);
            result.put("message", "Could not find book with that id in DB");
        }
        return result;
    }

    @Override
    public JSONObject deleteItem(HttpSession session, JSONObject item) {
        JSONObject result = new JSONObject();
        Integer deleteId = Integer.valueOf((String) item.get("id"));
        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");

        if (exists(deleteId, cart) == -1) {
            result.put("erroe", true);
            result.put("message", "Could not find that book in your cart!");
            result.put("total", calculateTotal(cart));
        } else {
            result.put("message", "Book " + cart.get(exists(deleteId,cart)).getBook().getName() + " deleted from cart!");
            cart.remove(exists(deleteId,cart));
            result.put("error", false);
            result.put("total", calculateTotal(cart));
        }
        session.setAttribute("cart", cart);
        return result;
    }

    private int exists(Integer id, List<CartItem> cart) {
        for (int i = 0; i < cart.size(); i++) {
            if (cart.get(i).getBook().getId().equals(id)) {
                return i;
            }
        }
        return -1;
    }

    private float calculateTotal(List<CartItem> cart){
        float total = 0;
        for (CartItem item : cart){
            total += item.getBook().getPrice() * item.getQuantity();
        }
        return total;
    }

}
