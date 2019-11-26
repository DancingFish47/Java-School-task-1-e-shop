package com.rychkov.eshop.services;

import com.rychkov.eshop.exceptions.GenreException;

/**
 * Interface for genre related methods.
 *
 * @author Roman Rychkov
 * @see #deleteGenre(Integer)
 * @see #addGenre(String)
 * @see #editGenre(Integer, String)
 */
public interface GenreService {

    /**
     * Deletes genre from db.
     *
     * @param id Id of the genre to delete.
     * @throws GenreException when something happened during persisting data.
     */
    void deleteGenre(Integer id) throws GenreException;

    /**
     * Add new genre into db.
     *
     * @param name New genre name.
     * @throws GenreException when something happened during persisting data.
     */
    void addGenre(String name) throws GenreException;

    /**
     * Edits name of already existing genre.
     *
     * @param genreId Id of the genre to edit.
     * @param newName New nage for the genre.
     * @throws GenreException when something happened during persisting data.
     */
    void editGenre(Integer genreId, String newName) throws GenreException;
}
