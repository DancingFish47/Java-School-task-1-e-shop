package com.rychkov.eshop.services.implementations;

import com.rychkov.eshop.dtos.AddItemDto;
import com.rychkov.eshop.dtos.Cart;
import com.rychkov.eshop.dtos.CartItem;
import com.rychkov.eshop.entities.Book;
import com.rychkov.eshop.entities.Order;
import com.rychkov.eshop.exceptions.BookException;
import com.rychkov.eshop.exceptions.OutOfStockException;
import com.rychkov.eshop.repositories.BooksRepository;
import com.rychkov.eshop.repositories.OrderStatusRepository;
import com.rychkov.eshop.repositories.OrdersRepository;
import com.rychkov.eshop.services.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
    public void addItem(Cart cart, AddItemDto item) throws OutOfStockException {
        Integer addBookId = item.getId();
        Integer quantity = item.getQuantity();
        Optional<Book> optionalBook = booksRepository.findById(addBookId);
        if (optionalBook.isPresent()) {
            Book book = optionalBook.get();
            if (quantity > book.getAmount())
                throw new OutOfStockException("There are only " + book.getAmount() + " copies of " + book.getName() +
                        "left, while you are trying to add " + quantity);
            CartItem cartItem = new CartItem(book, quantity);

            List<CartItem> cartItems = cart.getCartItems();
            if (exists(addBookId, cartItems) == -1) {
                cartItems.add(cartItem);
            } else {
                cartItems.get(exists(addBookId, cartItems)).addQuantity(quantity);
            }
        } else {
            throw new BookException("Could not find that book in db");
        }
    }

    @Transactional
    @Override
    public void deleteItem(Cart cart, Integer deleteId) throws BookException {
        List<CartItem> cartItems = cart.getCartItems();

        if (exists(deleteId, cartItems) == -1) throw new BookException("Could not find that book in your cart!");
        else cartItems.remove(exists(deleteId, cartItems));
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
    public int checkStocksAndCreateTempOrder(Cart cart) throws OutOfStockException {
        List<Book> orderBooks = new ArrayList<>();
        List<CartItem> cartItems = cart.getCartItems();
        for (CartItem item : cartItems) {
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
        return ordersRepository.save(order).getId();
    }


}
