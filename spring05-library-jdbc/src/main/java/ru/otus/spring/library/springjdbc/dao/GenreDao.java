package ru.otus.spring.library.springjdbc.dao;

import ru.otus.spring.library.springjdbc.domain.Genre;

import java.util.List;

public interface GenreDao {

    Genre getById(int id);

    Genre getByName(String genreName);

    List<Genre> getAll();

    void insert(Genre genre);

    void update(Genre oldGenre, String newGenreName);

}
