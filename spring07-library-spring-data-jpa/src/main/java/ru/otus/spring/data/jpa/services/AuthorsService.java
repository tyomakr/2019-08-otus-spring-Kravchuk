package ru.otus.spring.data.jpa.services;

import ru.otus.spring.data.jpa.domain.Author;

public interface AuthorsService {

    void findAllAuthors();

    void insertAuthor(String authorName);

    void updateAuthorName(String originalAuthorName, String changedAuthorName);

    Author getAuthorByName(String authorName);
}
