package com.rychkov.eshop.services.implementations;

import com.rychkov.eshop.dtos.AddItemDto;
import com.rychkov.eshop.dtos.CartItem;
import com.rychkov.eshop.entitys.Book;
import com.rychkov.eshop.entitys.Order;
import com.rychkov.eshop.exceptions.OutOfStockException;
import com.rychkov.eshop.repositorys.BooksRepository;
import com.rychkov.eshop.repositorys.OrderStatusRepository;
import com.rychkov.eshop.repositorys.OrdersRepository;
import com.rychkov.eshop.services.CartService;
import lombok.RequiredArgsConstructor;
import net.minidev.json.JSONObject;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {
    private final BooksRepository booksRepository;
    private final OrderStatusRepository orderStatusRepository;
    private final OrdersRepository ordersRepository;

    @Override
    public JSONObject addItem(HttpSession session, AddItemDto item) throws OutOfStockException {
        JSONObject result = new JSONObject();
        Integer addBookId = item.getId();
        Integer quantity = item.getQuantity();
        Optional<Book> optionalBook = booksRepository.findById(addBookId);
        if (optionalBook.isPresent()) {
            Book book = optionalBook.get();
            if (quantity > book.getAmount())
                throw new OutOfStockException("There are only " + book.getAmount() + " copies of " + book.getName() +
                        "left, while you are trying to add " + quantity);
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
                    cart.get(exists(addBookId, cart)).addQuantity(quantity);
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

    @Transactional
    @Override
    public JSONObject deleteItem(HttpSession session, Integer deleteId) {
        JSONObject result = new JSONObject();
        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");

        if (exists(deleteId, cart) == -1) {
            result.put("error", true);
            result.put("message", "Could not find that book in your cart!");
            result.put("total", calculateTotal(cart));
            return result;
        } else {
            result.put("error", false);
            result.put("message", "Book " + cart.get(exists(deleteId, cart)).getBook().getName() + " deleted from cart!");
            cart.remove(exists(deleteId, cart));
            result.put("total", calculateTotal(cart));
            session.setAttribute("cart", cart);
            return result;
        }
    }

    private int exists(Integer id, List<CartItem> cart) {
        for (int i = 0; i < cart.size(); i++) {
            if (cart.get(i).getBook().getId().equals(id)) {
                return i;
            }
        }
        return -1;
    }

    private float calculateTotal(List<CartItem> cart) {
        float total = 0;
        for (CartItem item : cart) {
            total += item.getBook().getPrice() * item.getQuantity();
        }
        return total;
    }

    @Transactional
    @Override
    public void checkStocksAndCreateTempOrder(HttpSession session) throws OutOfStockException {
        List<Book> orderBooks = new ArrayList<>();
        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
        for (CartItem item : cart) {
            Optional<Book> optionalBook = booksRepository.findById(item.getBook().getId());
            if (optionalBook.isPresent()) {
                Book book = optionalBook.get();
                if (book.getAmount() < item.getQuantity())
                    throw new OutOfStockException("Book " + item.getBook().getName() + " is out of stock");
                book.setAmount(book.getAmount() - item.getQuantity());
                for (int i = 0; i < item.getQuantity(); i++) orderBooks.add(item.getBook());
                booksRepository.save(book);
            } else throw new OutOfStockException("Book " + item.getBook().getName() + " is out of stock");
        }

        Order order = new Order();
        order.setOrderStatus(orderStatusRepository.findByName("TEMPORDER"));

        order.setBooks(orderBooks);
        session.setAttribute("orderId", ordersRepository.save(order).getId());
    }


}
