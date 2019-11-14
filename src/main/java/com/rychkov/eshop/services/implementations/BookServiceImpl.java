package com.rychkov.eshop.services.implementations;

import com.rychkov.eshop.dtos.BookDto;
import com.rychkov.eshop.entitys.Book;
import com.rychkov.eshop.exceptions.BookException;
import com.rychkov.eshop.repositorys.BookCategoryRepository;
import com.rychkov.eshop.repositorys.BooksRepository;
import com.rychkov.eshop.services.BookService;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {
    @Autowired
    private BooksRepository booksRepository;
    @Autowired
    private BookCategoryRepository bookCategoryRepository;
    @Autowired
    RabbitTemplate template;


    @Override
    public List<Book> prepareBooksList(Map<String, String> params) {
        List<Book> preparedBooks;
        if (params.get("category") == null) preparedBooks = (List<Book>) booksRepository.findAll();
        else preparedBooks = booksRepository.findAllByBookCategory_Name(params.get("category"));

        if (params.get("sortType") == null) return preparedBooks;
        else {
            switch (params.get("sortType")) {
                case "price":
                    preparedBooks.sort(Comparator.comparing(Book::getPrice));
                    break;
                case "name":
                    preparedBooks.sort(Comparator.comparing(Book::getName));
                    break;
                default:
                    break;
            }

        }
        return preparedBooks;
    }

    @Override
    public void deleteBookById(Integer bookId) throws BookException {
        booksRepository.deleteById(bookId);
        template.convertAndSend("deleteBook", bookId);
        if (booksRepository.findById(bookId).isPresent()) throw new BookException("Failed to delete book");
    }

    @Override
    public void addNewBook(BookDto bookDto) throws BookException {
        Book book = new Book();
        BookDtoToBook(bookDto, book);
        //Could use Mapper here
        booksRepository.save(book);
        template.convertAndSend("addBook", book);
    }

    @Override
    public void editBook(BookDto bookDto) throws BookException {
        Optional<Book> optionalBook = booksRepository.findById(bookDto.getId());
        if (optionalBook.isPresent()) {
            Book book = optionalBook.get();
            BookDtoToBook(bookDto, book);
            //Could use Mapper here
            if (booksRepository.save(book) == null) throw new BookException("Failed to edit book details");
            template.convertAndSend("editBook", book);
        } else throw new BookException("Failed to edit book details");
    }

    private void BookDtoToBook(BookDto bookDto, Book book) {
        if (bookDto.getId() != null) book.setId(bookDto.getId());
        book.setName(bookDto.getName());
        book.setAuthor(bookDto.getAuthor());
        book.setSold(bookDto.getSold());
        book.setAmount(bookDto.getAmount());
        book.setPages(bookDto.getPages());
        book.setPrice(bookDto.getPrice());
        if (!bookDto.getGenre().isEmpty()) book.setBookCategory(bookCategoryRepository.findByName(bookDto.getGenre()));
    }


}
