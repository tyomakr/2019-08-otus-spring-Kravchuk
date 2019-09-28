package ru.otus.spring.library.springjdbc.service.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.spring.library.springjdbc.dao.impl.AuthorDaoJdbc;
import ru.otus.spring.library.springjdbc.domain.Author;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@JdbcTest
@Import({AuthorDaoJdbc.class, AuthorServiceImpl.class})
@DisplayName("Сервис для работы с авторами")
class AuthorServiceImplTest {

    @Autowired
    private AuthorServiceImpl authorService;

    @Autowired
    private AuthorDaoJdbc authorDao;

    @Test
    void isAuthorExists() {
        Author author = authorDao.getById(1);
        assertTrue(authorService.isAuthorExists(author));
    }
}