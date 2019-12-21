package ru.otus.spring.library.webmvc.service;

import org.springframework.security.access.prepost.PostFilter;
import ru.otus.spring.library.webmvc.domain.Book;

import java.util.List;
import java.util.Optional;

public interface BookService {

    @PostFilter("hasPermission(filterObject, 'READ')")
    List<Book> findAll();

    Optional<Book> findById(String bookId);

    Book insertBook(String bookTitle, String bookAuthor, String bookGenre);

    void updateBookTitle(Book book);

    void addBookAuthor(String bookId, String bookAuthor);

    void removeBookAuthor(String bookId, String bookAuthor);

    void addBookGenre(String bookId, String bookGenre);

    void removeBookGenre(String bookId, String bookGenre);

    void removeBook(String bookId);
}
