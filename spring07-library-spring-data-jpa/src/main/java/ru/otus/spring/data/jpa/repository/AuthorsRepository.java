package ru.otus.spring.data.jpa.repository;


import ru.otus.spring.data.jpa.domain.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorsRepository {

    List <Author> findAllAuthors();

    Author findAuthorById(long id);

    Optional<Author> findAuthorByName(String authorName);

    Author saveAuthor(Author author);
}
