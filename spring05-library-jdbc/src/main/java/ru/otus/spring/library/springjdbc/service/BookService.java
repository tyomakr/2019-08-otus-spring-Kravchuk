package ru.otus.spring.library.springjdbc.service;

import ru.otus.spring.library.springjdbc.domain.Book;

public interface BookService {

    void getAllBooks();

    void insertBook(String bookName, String authorName, String genreName);

    void updateBook(String oldBookName, String authorName, String newBookName);

    void deleteBook(String bookId);

    boolean isBookExists(Book book);

    void updateBookAuthor(String bookName, String bookOldAuthor, String bookNewAuthor);

    void updateBookGenre(String bookName, String oldGenre, String newGenre);

}
