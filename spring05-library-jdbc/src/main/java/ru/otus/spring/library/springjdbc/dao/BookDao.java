package ru.otus.spring.library.springjdbc.dao;

import ru.otus.spring.library.springjdbc.domain.Book;

import java.util.List;

public interface BookDao {

    Book getById(int id);

    Book getByName(String bookName, int authorId);

    List<Book> getAll();

    void insert(Book book);

    void update(Book book, String newBookName);

    void updateBookAuthor(Book book, int oldAuthorId, int newAuthorId);

    void updateBookGenre(int bookId, int newGenreId);

    void deleteById(int id);
}
