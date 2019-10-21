package ru.otus.spring.data.jpa.repository;

import ru.otus.spring.data.jpa.domain.Book;

import java.util.List;

public interface BooksRepository {

    List<Book> findAllBooks();

    Book findBookById(long id);

    Book saveBook(Book book);

    void deleteBook(Book book);
}
