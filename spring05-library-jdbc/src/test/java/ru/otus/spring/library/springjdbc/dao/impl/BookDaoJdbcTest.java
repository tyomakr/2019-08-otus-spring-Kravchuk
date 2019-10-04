package ru.otus.spring.library.springjdbc.dao.impl;

import org.junit.Assert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.spring.library.springjdbc.domain.Author;
import ru.otus.spring.library.springjdbc.domain.Book;
import ru.otus.spring.library.springjdbc.domain.Genre;

import static org.junit.jupiter.api.Assertions.*;


@JdbcTest
@Import({BookDaoJdbc.class, AuthorDaoJdbc.class, GenreDaoJdbc.class})
@DisplayName("DAO для работы с книгами")
class BookDaoJdbcTest {

    private static final int BOOKS_COUNT = 31;
    private static final Book EXAMPLE_BOOK_20 = new Book(20, "Гиперион", new Author(4,"Дэн Симмонс"), new Genre(1,"Фантастика и фэнтези"));
    private static final String TB = "TestBook";
    private static final String TA = "TestAuthor";
    private static final String TG = "TestGenre";

    @Autowired
    private BookDaoJdbc bookDao;
    @Autowired
    private AuthorDaoJdbc authorDao;
    @Autowired
    private GenreDaoJdbc genreDao;

    @Test
    @DisplayName("getById получает книгу по ИД")
    void getById() {
        assertEquals(bookDao.getById(20).getBookName(), EXAMPLE_BOOK_20.getBookName());
    }

    @Test
    @DisplayName("getByName получает книгу по названию")
    void getByName() {
        assertEquals(bookDao.getByName("Гиперион", 4), EXAMPLE_BOOK_20);
    }

    @Test
    @DisplayName("getAll получает все книги")
    void getAll() {
        assertEquals(bookDao.getAll().size(), BOOKS_COUNT);
    }

    @Test
    @DisplayName("insert добавляет новую книгу в базу")
    void insert() {
        Author testAuthor = new Author(TA);
        authorDao.insert(testAuthor);
        Genre testGenre = new Genre(TG);
        genreDao.insert(testGenre);
        Book book = new Book(TB, authorDao.getByName(testAuthor.getAuthorName()), genreDao.getByName(testGenre.getGenreName()));
        bookDao.insert(book);

        assertSame(bookDao.getByName(book.getBookName(), authorDao.getByName(testAuthor.getAuthorName()).getId()).getBookName(), book.getBookName());
    }

    @Test
    @DisplayName("update обновляет название книги")
    void update() {
        Book book = getTestBook();

        long authorId = authorDao.getByName(book.getAuthor().getAuthorName()).getId();
        book = bookDao.getByName(book.getBookName(), authorId);

        String newBookName = "TEST BOOK RENAMED";
        bookDao.update(book, newBookName);
        Book newBook = bookDao.getByName(newBookName, authorId);
        assertSame(newBook.getId(), book.getId());
        assertNotSame(newBook.getBookName(), book.getBookName());
    }


    @Test
    @DisplayName("updateBookAuthor обновляет имя автора в книге")
    void updateBookAuthor() {
        Book book = getTestBook();

        long authorID = authorDao.getByName(TA).getId();
        book = bookDao.getByName(TB, authorID);

        Author newAuthor = new Author("NEW AUTHOR");
        authorDao.insert(newAuthor);
        long newAuthorID = authorDao.getByName(newAuthor.getAuthorName()).getId();

        bookDao.updateBookAuthor(book, authorID, newAuthorID);
        Book editedBook = bookDao.getByName(book.getBookName(), newAuthorID);

        assertSame(book.getId(), editedBook.getId());
        assertNotSame(book.getAuthor().getId(), editedBook.getAuthor().getId());
    }

    @Test
    @DisplayName("updateBookAuthor обновляет название жанра в книге")
    void updateBookGenre() {
        Book book = getTestBook();

        Genre newGenre = new Genre("NEW GENRE");
        genreDao.insert(newGenre);

        long bookId = book.getId();
        long newGenreId = genreDao.getByName(newGenre.getGenreName()).getId();

        bookDao.updateBookGenre(bookId, newGenreId);
        Book newBook = bookDao.getById(bookId);

        assertSame(book.getId(), newBook.getId());
        assertNotSame(book.getGenre(), newBook.getGenre());
    }

    @Test
    @DisplayName("deleteById удаляет книгу по ID")
    void deleteById() {
        Book book = getTestBook();
        bookDao.deleteById(book.getId());

        try {
            bookDao.getById(book.getId());
            Assert.fail();
        } catch (EmptyResultDataAccessException e) {
        }
    }


    @Test
    @DisplayName(" при проверке наличия книги должен возвращать true если книга действительно присутствует в БД")
    void isAuthorNameExistsShouldReturnTrueWhenCheckReallyExistingAuthor() {
        Assert.assertTrue(bookDao.isBookNameWithAuthorNameExists(EXAMPLE_BOOK_20));
    }

    @Test
    @DisplayName(" при проверке наличия книги должен возвращать false если книги нет в БД")
    void isAuthorNameExistsShouldReturnFalseWhenCheckNotExistingAuthor() {
        Book notExistingAuthor = new Book(1000, "NotExistingBook", new Author(TA), new Genre(TG));
        Assert.assertFalse(bookDao.isBookNameWithAuthorNameExists(notExistingAuthor));
    }


    private Book getTestBook() {
        Author testAuthor = new Author(TA);
        authorDao.insert(testAuthor);
        Genre testGenre = new Genre(TG);
        genreDao.insert(testGenre);
        Book book = new Book(TB, authorDao.getByName(testAuthor.getAuthorName()), genreDao.getByName(testGenre.getGenreName()));
        bookDao.insert(book);

        book = bookDao.getByName(book.getBookName(), testAuthor.getId());

        return book;
    }


}
