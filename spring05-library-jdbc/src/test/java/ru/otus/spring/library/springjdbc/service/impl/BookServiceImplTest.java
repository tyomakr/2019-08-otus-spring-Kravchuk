package ru.otus.spring.library.springjdbc.service.impl;

import org.junit.Assert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.spring.library.springjdbc.dao.impl.AuthorDaoJdbc;
import ru.otus.spring.library.springjdbc.dao.impl.BookDaoJdbc;
import ru.otus.spring.library.springjdbc.dao.impl.GenreDaoJdbc;
import ru.otus.spring.library.springjdbc.domain.Book;

@ExtendWith(SpringExtension.class)
@JdbcTest
@Import({BookDaoJdbc.class, AuthorDaoJdbc.class, GenreDaoJdbc.class, BookServiceImpl.class, AuthorServiceImpl.class, GenreServiceImpl.class})
@DisplayName("Сервис для работы с книгами")
class BookServiceImplTest {


    @Autowired
    private BookServiceImpl booksService;
    @Autowired
    private BookDaoJdbc bookDao;

    @Test
    void isBookExists() {
        Book book = bookDao.getById(1);

        Assert.assertTrue(booksService.isBookExists(book));

    }

}