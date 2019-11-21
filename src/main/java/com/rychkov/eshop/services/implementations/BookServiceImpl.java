package com.rychkov.eshop.services.implementations;

import com.rychkov.eshop.dtos.BookDto;
import com.rychkov.eshop.entitys.Book;
import com.rychkov.eshop.exceptions.BookException;
import com.rychkov.eshop.repositorys.BookCategoryRepository;
import com.rychkov.eshop.repositorys.BooksRepository;
import com.rychkov.eshop.services.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class BookServiceImpl implements BookService {

    private final BooksRepository booksRepository;

    private final BookCategoryRepository bookCategoryRepository;

    private final RabbitTemplate template;

    private final Environment env;


    @Override
    public Page<Book> prepareBooksList(Map<String, String> params) {
        Page<Book> preparedBooks;

        int booksByPage = Integer.parseInt(Objects.requireNonNull(env.getProperty("books.by.page")));

        if (params.get("sortType") == null) {
            if (params.get("category") == null)
                preparedBooks = booksRepository.findAll(PageRequest.of(Integer.parseInt(params.get("page")) - 1, booksByPage));
            else
                preparedBooks = booksRepository.findAllByBookCategory_Name(params.get("category"), PageRequest.of(Integer.parseInt(params.get("page")) - 1, booksByPage));
        } else {
            if (params.get("category") == null)
                preparedBooks = booksRepository.findAll(PageRequest.of(Integer.parseInt(params.get("page")) - 1, booksByPage, Sort.by(Sort.Direction.ASC, params.get("sortType"))));
            else {
                preparedBooks = booksRepository.findAllByBookCategory_Name(params.get("category"), PageRequest.of(Integer.parseInt(params.get("page")) - 1, booksByPage, Sort.by(Sort.Direction.ASC, params.get("sortType"))));
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
        if (bookDto.getGenre() != null && !bookDto.getGenre().isEmpty())
            book.setBookCategory(bookCategoryRepository.findByName(bookDto.getGenre()));
    }


}
