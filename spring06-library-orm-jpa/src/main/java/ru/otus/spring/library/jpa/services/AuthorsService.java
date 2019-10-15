package ru.otus.spring.library.jpa.services;

public interface AuthorsService {

    void findAll();

    void insertAuthor(String authorName);

    void updateAuthorName(String originalAuthorName, String changedAuthorName);
}
