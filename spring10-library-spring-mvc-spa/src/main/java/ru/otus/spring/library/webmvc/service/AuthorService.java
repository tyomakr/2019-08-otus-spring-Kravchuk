package ru.otus.spring.library.webmvc.service;

import ru.otus.spring.library.webmvc.domain.Author;

public interface AuthorService {

    Author findOrCreateAuthor(String bookAuthor);
}
