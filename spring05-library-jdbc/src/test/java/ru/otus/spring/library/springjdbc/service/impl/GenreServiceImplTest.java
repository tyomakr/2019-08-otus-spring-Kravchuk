package ru.otus.spring.library.springjdbc.service.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.spring.library.springjdbc.dao.impl.GenreDaoJdbc;
import ru.otus.spring.library.springjdbc.domain.Genre;
import ru.otus.spring.library.springjdbc.service.IOService;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(SpringExtension.class)
@JdbcTest
@Import({GenreDaoJdbc.class, GenreServiceImpl.class})
@DisplayName("Сервис для работы с жанрами")
class GenreServiceImplTest {

    @Autowired
    private GenreServiceImpl genreService;

    @Autowired
    private GenreDaoJdbc genreDao;

    @MockBean
    private IOService ioService;

    @Test
    void isGenreExists() {
        Genre genre = genreDao.getById(1);
        assertTrue(genreService.isGenreExists(genre));
    }
}