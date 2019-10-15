package ru.otus.spring.library.jpa.repository;

import ru.otus.spring.library.jpa.domain.Book;

import java.util.List;

public interface BooksRepository {

    List<Book> findAllBooks();

}
