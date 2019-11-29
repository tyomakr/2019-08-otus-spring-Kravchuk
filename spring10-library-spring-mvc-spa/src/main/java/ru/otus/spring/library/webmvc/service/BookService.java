package ru.otus.spring.library.webmvc.service;

import ru.otus.spring.library.webmvc.domain.Book;

import java.util.List;
import java.util.Optional;

public interface BookService {

    List<Book> findAll();

    Optional<Book> findById(String bookId);

    Book insertBook(String bookTitle, String bookAuthor, String bookGenre);

    Book updateBook(Book book);

    void removeBook(String bookId);
}
