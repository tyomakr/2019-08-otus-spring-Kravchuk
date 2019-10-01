package ru.otus.spring.library.springjdbc.service.impl;

import org.junit.Assert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.spring.library.springjdbc.dao.AuthorDao;
import ru.otus.spring.library.springjdbc.dao.GenreDao;
import ru.otus.spring.library.springjdbc.dao.impl.AuthorDaoJdbc;
import ru.otus.spring.library.springjdbc.dao.impl.BookDaoJdbc;
import ru.otus.spring.library.springjdbc.dao.impl.GenreDaoJdbc;
import ru.otus.spring.library.springjdbc.domain.Book;
import ru.otus.spring.library.springjdbc.service.AuthorService;
import ru.otus.spring.library.springjdbc.service.IOService;

@ExtendWith(SpringExtension.class)
@JdbcTest
@DisplayName("Сервис для работы с книгами")
class BookServiceImplTest {


    @SpyBean
    private BookServiceImpl booksService;
    @SpyBean
    private BookDaoJdbc bookDao;

    @MockBean
    private AuthorServiceImpl authorService;
    @MockBean
    private AuthorDaoJdbc authorDao;
    @MockBean
    private GenreServiceImpl genreService;
    @MockBean
    private GenreDaoJdbc genreDao;
    @MockBean
    private IOService ioService;


    @Test
    void isBookExists() {
        Book book = bookDao.getById(1);

        Assert.assertTrue(booksService.isBookExists(book));

    }

}