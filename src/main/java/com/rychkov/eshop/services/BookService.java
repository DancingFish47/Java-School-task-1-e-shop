package com.rychkov.eshop.services;

import com.rychkov.eshop.dtos.BookDto;
import com.rychkov.eshop.dtos.PageParams;
import com.rychkov.eshop.entities.Book;
import com.rychkov.eshop.exceptions.BookException;
import org.springframework.data.domain.Page;

/**
 * Interface for book related methods.
 *
 * @author Roman Rychkov
 * @see #prepareBooksList(PageParams)
 * @see #deleteBookById(Integer)
 * @see #addNewBook(BookDto)
 * @see #editBook(BookDto)
 */
public interface BookService {

    /**
     * Prepares page of books for main page
     *
     * @param pageParams contains params for sorting type, book's genre and needed page.
     * @return Page of Book objects
     */
    Page<Book> prepareBooksList(PageParams pageParams);

    /**
     * Deletes book from database.
     *
     * @param bookId Id of the book to delete.
     * @throws BookException when something happened during persisting data.
     */
    void deleteBookById(Integer bookId) throws BookException;

    /**
     * Adds book in database.
     *
     * @param bookDto contains information about book: name, author etc.
     * @throws BookException when something happened during persisting data.
     */
    void addNewBook(BookDto bookDto) throws BookException;

    /**
     * Edits information about already existing book in database.
     *
     * @param bookDto contains new information for book: name, author etc.
     * @throws BookException when something happened during persisting data.
     */
    void editBook(BookDto bookDto) throws BookException;
}
