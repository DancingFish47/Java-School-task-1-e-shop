package com.rychkov.eshop.services;

import com.rychkov.eshop.exceptions.GenreException;

public interface GenreService {
    void deleteGenre(Integer id) throws GenreException;

    void addGenre(String name) throws GenreException;

    void editGenre(Integer genreId, String newName) throws GenreException;
}
