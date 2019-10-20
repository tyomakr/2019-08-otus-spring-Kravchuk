package ru.otus.spring.library.jpa.repository.impl;

import lombok.val;
import org.junit.Assert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.spring.library.jpa.domain.Author;
import ru.otus.spring.library.jpa.domain.Book;
import ru.otus.spring.library.jpa.domain.Genre;

@DataJpaTest
@Import({BooksRepositoryJpa.class})
@DisplayName("Репозиторий для работы с книгами")
class BooksRepositoryJpaTest {

    private static final int BOOKS_QTY = 32;
    private static final Author TEST_AUTHOR = new Author("TEST_AUTHOR");
    private static final Genre TEST_GENRE = new Genre("TEST_GENRE");
    private static final Long BOOK_TEST_2_ID = 2L;
    private static final String BOOK_TEST_2_NAME = "Сильмариллион";
    private static final Book BOOK_TEST_33 = new Book("TEST_BOOK", TEST_AUTHOR, TEST_GENRE);


    @Autowired
    BooksRepositoryJpa booksRepo;

    @Autowired
    TestEntityManager em;


    @Test
    @DisplayName("успешно получает все книги из базы")
    void shouldFindAllBooks() {
        val books = booksRepo.findAllBooks();
        Assert.assertEquals(books.size(), BOOKS_QTY);
    }

    @Test
    @DisplayName("успешно получает книгу по ее ID")
    void shouldFindBookById() {
        val eBook = booksRepo.findBookById(BOOK_TEST_2_ID);
        val aBook = em.find(Book.class, BOOK_TEST_2_ID);
        Assert.assertEquals(eBook, aBook);
        Assert.assertEquals(eBook.getBookName(), BOOK_TEST_2_NAME);
    }

    @Test
    @DisplayName("успешно сохраняет книгу в БД")
    void shouldSaveBook() {
        Book aBook = booksRepo.saveBook(BOOK_TEST_33);
        Assert.assertEquals(BOOK_TEST_33, aBook);
        Book bBook = booksRepo.findBookById(33);
        Assert.assertEquals(BOOK_TEST_33, bBook);
    }

    @Test
    @DisplayName("удаляет книгу из БД")
    void shouldDeleteBook() {
        val book = em.find(Book.class, BOOK_TEST_2_ID);
        booksRepo.deleteBook(book);
        Assert.assertNull(booksRepo.findBookById(BOOK_TEST_2_ID));
        val books = booksRepo.findAllBooks();
        Assert.assertEquals(books.size(), BOOKS_QTY - 1);
    }
}