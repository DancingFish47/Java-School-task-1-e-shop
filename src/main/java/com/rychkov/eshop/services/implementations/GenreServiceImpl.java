package com.rychkov.eshop.services.implementations;

import com.rychkov.eshop.entities.Book;
import com.rychkov.eshop.entities.BookCategory;
import com.rychkov.eshop.exceptions.GenreException;
import com.rychkov.eshop.repositories.BookCategoryRepository;
import com.rychkov.eshop.repositories.BooksRepository;
import com.rychkov.eshop.services.GenreService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.rychkov.eshop.configurations.RabbitConfiguration.EDIT_QUEUE_NAME;

@RequiredArgsConstructor
@Service
public class GenreServiceImpl implements GenreService {
    private final BookCategoryRepository bookCategoryRepository;
    private final BooksRepository booksRepository;
    private final RabbitTemplate rabbitTemplate;

    @Override
    public void deleteGenre(Integer id) throws GenreException {
        Optional<BookCategory> optionalBookCategory = bookCategoryRepository.findById(id);
        if (optionalBookCategory.isPresent()) {
            BookCategory bookCategory = optionalBookCategory.get();
            List<Book> books = booksRepository.findAllByBookCategory(bookCategory);
            for (Book book : books) {
                book.setBookCategory(null);
                rabbitTemplate.convertAndSend(EDIT_QUEUE_NAME, book);
                booksRepository.save(book);
            }
            bookCategoryRepository.deleteById(id);
        } else throw new GenreException("Failed to delete Genre");
    }

    @Override
    public void addGenre(String name) throws GenreException {
        BookCategory bookCategory = new BookCategory();
        bookCategory.setName(name);
        if (bookCategoryRepository.findByName(name) != null) throw new GenreException("This category already exists!");
        bookCategoryRepository.save(bookCategory);
    }

    @Override
    public void editGenre(Integer genreId, String newName) throws GenreException {
        Optional<BookCategory> optionalBookCategory = bookCategoryRepository.findById(genreId);
        if (optionalBookCategory.isPresent()) {
            BookCategory bookCategory = optionalBookCategory.get();
            bookCategory.setName(newName);
            bookCategoryRepository.save(bookCategory);

            List<Book> books = booksRepository.findAllByBookCategory(bookCategory);
            for (Book book : books) {
                rabbitTemplate.convertAndSend(EDIT_QUEUE_NAME, book);
                booksRepository.save(book);
            }
        } else {
            throw new GenreException("Failed to find Genre with that ID");
        }
    }


}
