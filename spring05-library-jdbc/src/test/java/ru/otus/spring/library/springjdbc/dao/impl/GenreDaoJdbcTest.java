package ru.otus.spring.library.springjdbc.dao.impl;

import org.junit.Assert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.spring.library.springjdbc.domain.Genre;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@JdbcTest
@Import(GenreDaoJdbc.class)
@DisplayName("DAO для работы с жанром")
class GenreDaoJdbcTest {

    private final static int GENRES_COUNT = 5;
    private final static String GENRE_ID_2_NAME = "Детектив";
    private final static String GENRE_TEST_NAME = "Test Genre";
    private final static String GENRE_TEST_NAME_REN = "Test Genre renamed";

    @Autowired
    private GenreDaoJdbc genreDao;

    @Test
    @DisplayName("getById получает жанр по Id")
    void getById() {
        Assert.assertEquals(genreDao.getById(2).getGenreName(), GENRE_ID_2_NAME);
    }

    @Test
    @DisplayName("getByName получает жанр по имени")
    void getByName() {
        Assert.assertEquals(genreDao.getByName(GENRE_ID_2_NAME).getId(), 2);
    }

    @Test
    @DisplayName("getAll получает все предварительно внесенные жанры")
    void getAll() {
        assertEquals(genreDao.getAll().size(), GENRES_COUNT);
    }

    @Test
    @DisplayName("insert добавляет жанр в базу")
    void insert() {
        Genre testGenre = new Genre(GENRE_TEST_NAME);
        genreDao.insert(testGenre);
        Assert.assertSame(testGenre.getGenreName(), genreDao.getByName(GENRE_TEST_NAME).getGenreName());
    }

    @Test
    @DisplayName("update обновляет название жанра")
    void update() {
        Genre testGenre = new Genre(GENRE_TEST_NAME);
        genreDao.insert(testGenre);
        testGenre = genreDao.getByName(GENRE_TEST_NAME);
        int expId = testGenre.getId();
        genreDao.update(testGenre, GENRE_TEST_NAME_REN);

        Genre newTestGenre = genreDao.getById(expId);
        assertSame(testGenre.getId(), newTestGenre.getId());
        assertNotSame(testGenre.getGenreName(), newTestGenre.getGenreName());
    }
}