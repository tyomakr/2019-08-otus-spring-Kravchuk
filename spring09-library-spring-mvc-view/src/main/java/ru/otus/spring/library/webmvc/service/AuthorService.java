package ru.otus.spring.library.webmvc.service;

import ru.otus.spring.library.webmvc.domain.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorService {

    Optional<Author> findById(String authorId);

    List<Author> findAll();

    Author findOrCreateAuthor(String bookAuthor);
}
