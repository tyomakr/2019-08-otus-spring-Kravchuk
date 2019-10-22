package ru.otus.spring.library.jpa.services;

public interface BooksService {

    void findAllBooks();

    void addBook(String bookName, String authorName, String genreName);

    void addAuthorToBook(Long bookId, String authorName);

    void addGenreToBook(Long bookId, String genreName);

    void updateBookName(Long bookId, String newBookName);

    void updateBookAuthor(Long bookId, String originalBookAuthor, String changedBookAuthor);

    void updateBookGenre(Long bookId, String originalBookGenre, String newBookGenre);

    void deleteBook(Long id);

    void removeAuthorToBook(Long bookId, String authorName);

    void removeGenreToBook(Long bookId, String genreName);

}
