package ru.otus.spring.library.jpa.services;

import ru.otus.spring.library.jpa.domain.Genre;

public interface GenresService {

    void findAllGenres();

    void insertGenre(String genreName);

    void updateGenreName(String originalGenreName, String changedGenreName);

    Genre getGenreByName(String genreName);
}
