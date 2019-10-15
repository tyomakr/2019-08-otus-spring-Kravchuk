package ru.otus.spring.library.jpa.services;

public interface GenresService {

    void findAll();

    void insertGenre(String genreName);

    void updateGenreName(String originalGenreName, String changedGenreName);
}
