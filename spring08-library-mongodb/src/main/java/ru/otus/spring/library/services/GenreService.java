package ru.otus.spring.library.services;

public interface GenreService {

    void findAll();

    void insertGenre(String genreTitle);

    void updateGenre(String oldGenreTitle, String newGenreTitle);
}
