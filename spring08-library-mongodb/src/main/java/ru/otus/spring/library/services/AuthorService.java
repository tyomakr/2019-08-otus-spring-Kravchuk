package ru.otus.spring.library.services;

public interface AuthorService {

    void findAll();

    void insertAuthor(String authorName);

    void updateAuthor(String oldAuthorName, String newAuthorName);
}
