package ru.otus.spring.library.springjdbc.service;

import ru.otus.spring.library.springjdbc.domain.Genre;

public interface GenreService {

    void getAllGenres();

    void addGenre(String genreName);

    void updateGenre(String oldGenreName, String newGenreName);

    boolean isGenreNameExists(Genre genre);

}
