package ru.otus.spring.library.jpa.repository;

import ru.otus.spring.library.jpa.domain.Genre;

import java.util.List;

public interface GenresRepository {

    List <Genre> findAllGenres();

    Genre findGenreById(long id);

    Genre findGenreByName(String genreName);

    void insertGenre(Genre genre);

    void updateGenre(Genre genre);

    boolean isExists(String genreName);
}
