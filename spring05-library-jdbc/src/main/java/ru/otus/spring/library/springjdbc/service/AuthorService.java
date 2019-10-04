package ru.otus.spring.library.springjdbc.service;

import ru.otus.spring.library.springjdbc.domain.Author;

public interface AuthorService {

    void getAllAuthors();

    void addAuthor(String authorName);

    void updateAuthor(String oldAuthorName, String newAuthorName);

    boolean isAuthorNameExists(Author author);
}
