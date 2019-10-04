package ru.otus.spring.library.springjdbc.dao;

import ru.otus.spring.library.springjdbc.domain.Book;

import java.util.List;

public interface BookDao {

    Book getById(long id);

    Book getByName(String bookName, long authorId);

    List<Book> getAll();

    void insert(Book book);

    void update(Book book, String newBookName);

    void updateBookAuthor(Book book, long oldAuthorId, long newAuthorId);

    void updateBookGenre(long bookId, long newGenreId);

    void deleteById(long id);

    boolean isBookNameWithAuthorNameExists(Book book);
}
