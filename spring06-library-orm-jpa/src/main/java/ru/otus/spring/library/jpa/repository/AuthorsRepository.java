package ru.otus.spring.library.jpa.repository;

import ru.otus.spring.library.jpa.domain.Author;

import java.util.List;

public interface AuthorsRepository {

    List <Author> findAllAuthors();

    Author findAuthorById(long id);

    Author findAuthorByName(String authorName);

    void saveAuthor(Author author);

//    void updateAuthor(Author author);

//    boolean isExists(String authorName);
}
