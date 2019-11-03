package ru.otus.spring.library.webmvc.service;

import ru.otus.spring.library.webmvc.domain.Book;

import java.util.List;
import java.util.Optional;

public interface BookService {

    List<Book> findAll();

    Book findById(String bookId);

    void insertBook(String bookTitle, String bookAuthor, String bookGenre);
}
