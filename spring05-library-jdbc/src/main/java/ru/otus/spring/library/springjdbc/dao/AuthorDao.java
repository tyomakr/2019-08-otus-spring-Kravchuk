package ru.otus.spring.library.springjdbc.dao;

import ru.otus.spring.library.springjdbc.domain.Author;

import java.util.List;

public interface AuthorDao {

    Author getById(int id);

    Author getByName(String authorName);

    List<Author> getAll();

    void insert(Author author);

    void update(Author oldAuthor, String newAuthorName);

}
