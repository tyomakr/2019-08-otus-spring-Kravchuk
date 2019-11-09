package ru.otus.spring.library.services;

public interface BookService {

    void findAll();

    void insertBook(String bookTitle, String bookAuthor, String bookGenre);

    void updateBookName(String bookId, String newBookTitle);

    void updateBookAuthor(String bookId, String oldBookAuthor, String newBookAuthor);

    void updateBookGenre(String bookId, String oldBookGenre, String newBookGenre);

    void addBookAuthor(String bookId, String bookAuthor);

    void removeBookAuthor(String bookId, String bookAuthor);

    void addBookGenre(String bookId, String bookGenre);

    void removeBookGenre(String bookId, String bookGenre);

    void removeBook(String bookId);
}
