package com.rychkov.eshop.services;

import com.rychkov.eshop.entitys.Book;
import com.rychkov.eshop.entitys.BookCategory;
import com.rychkov.eshop.exceptions.GenreException;
import com.rychkov.eshop.repositorys.BookCategoryRepository;
import com.rychkov.eshop.repositorys.BooksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GenreServiceImpl implements GenreService {
    @Autowired
    BookCategoryRepository bookCategoryRepository;
    @Autowired
    BooksRepository booksRepository;

    @Override
    public void deleteGenre(Integer id) throws GenreException {
        Optional<BookCategory> optionalBookCategory = bookCategoryRepository.findById(id);
        if (optionalBookCategory.isPresent()) {
            BookCategory bookCategory = optionalBookCategory.get();
            List<Book> books = booksRepository.findAllByBookCategory(bookCategory);
            for (Book book : books) {
                book.setBookCategory(null);
                booksRepository.save(book);
            }
            bookCategoryRepository.deleteById(id);
            if (bookCategoryRepository.findById(id).isPresent()) throw new GenreException("Failed to delete Genre");
        } else throw new GenreException("Failed to delete Genre");
    }

    @Override
    public void addGenre(String name) throws GenreException {
        BookCategory bookCategory = new BookCategory();
        bookCategory.setName(name);
        if (bookCategoryRepository.findByName(name) != null) throw new GenreException("This category already exists!");
        if (bookCategoryRepository.save(bookCategory) == null) throw new GenreException("Failed to create new Genre");
    }

    @Override
    public void editGenre(Integer genreId, String newName) throws GenreException {
        Optional<BookCategory> optionalBookCategory = bookCategoryRepository.findById(genreId);
        if (optionalBookCategory.isPresent()) {
            BookCategory bookCategory = optionalBookCategory.get();
            bookCategory.setName(newName);
            if (bookCategoryRepository.save(bookCategory) == null) throw new GenreException("Failed to edit Genre");
        } else {
            throw new GenreException("Failed to find Genre with that ID");
        }
    }


}
