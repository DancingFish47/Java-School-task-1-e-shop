package com.rychkov.eshop.services.implementations;

import com.rychkov.eshop.dtos.BookDto;
import com.rychkov.eshop.dtos.PageParams;
import com.rychkov.eshop.entities.Book;
import com.rychkov.eshop.exceptions.BookException;
import com.rychkov.eshop.repositories.BookCategoryRepository;
import com.rychkov.eshop.repositories.BooksRepository;
import com.rychkov.eshop.services.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

import static com.rychkov.eshop.configurations.RabbitConfiguration.*;

@RequiredArgsConstructor
@Service
public class BookServiceImpl implements BookService {

    private final BooksRepository booksRepository;

    private final BookCategoryRepository bookCategoryRepository;

    private final RabbitTemplate template;

    private final Environment env;


    @Override
    public Page<Book> prepareBooksList(PageParams pageParams) {
        Page<Book> preparedBooks;

        int booksByPage = Integer.parseInt(Objects.requireNonNull(env.getProperty("books.by.page")));

        if (pageParams.getSort() == null) {
            if (pageParams.getCategory() == null)
                preparedBooks = booksRepository.findAll(PageRequest.of(pageParams.getPage() - 1, booksByPage));
            else
                preparedBooks = booksRepository.findAllByBookCategory_Name(pageParams.getCategory(), PageRequest.of(pageParams.getPage() - 1, booksByPage));
        } else {
            if (pageParams.getCategory() == null)
                preparedBooks = booksRepository.findAll(PageRequest.of(pageParams.getPage(), booksByPage, Sort.by(Sort.Direction.ASC, pageParams.getSort())));
            else {
                preparedBooks = booksRepository.findAllByBookCategory_Name(pageParams.getCategory(), PageRequest.of(pageParams.getPage() - 1, booksByPage, Sort.by(Sort.Direction.ASC, pageParams.getSort())));
            }
        }
        return preparedBooks;
    }

    @Override
    public void deleteBookById(Integer bookId) throws BookException {
        booksRepository.deleteById(bookId);
        template.convertAndSend(DELETE_QUEUE_NAME, bookId);
        if (booksRepository.findById(bookId).isPresent()) throw new BookException("Failed to delete book");
    }

    @Override
    public void addNewBook(BookDto bookDto) throws BookException {
        Book book = new Book();
        BookDtoToBook(bookDto, book);
        booksRepository.save(book);
        template.convertAndSend(ADD_QUEUE_NAME, book);
    }

    @Override
    public void editBook(BookDto bookDto) throws BookException {
        Optional<Book> optionalBook = booksRepository.findById(bookDto.getId());
        if (optionalBook.isPresent()) {
            Book book = optionalBook.get();
            BookDtoToBook(bookDto, book);
            booksRepository.save(book);
            template.convertAndSend(EDIT_QUEUE_NAME, book);
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
        if (bookDto.getGenre() != null && !bookDto.getGenre().isEmpty())
            book.setBookCategory(bookCategoryRepository.findByName(bookDto.getGenre()));
    }


}
