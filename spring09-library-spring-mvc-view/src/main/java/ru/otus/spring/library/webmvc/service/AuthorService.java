package ru.otus.spring.library.webmvc.service;

import ru.otus.spring.library.webmvc.domain.Author;

import java.util.List;

public interface AuthorService {

    List<Author> findAll();

    Author findOrCreateAuthor(String bookAuthor);
}
