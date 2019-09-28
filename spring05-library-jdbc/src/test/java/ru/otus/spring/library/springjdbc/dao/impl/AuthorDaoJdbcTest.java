package ru.otus.spring.library.springjdbc.dao.impl;

import org.junit.Assert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.spring.library.springjdbc.domain.Author;


@ExtendWith(SpringExtension.class)
@JdbcTest
@Import(AuthorDaoJdbc.class)
@DisplayName("DAO для работы с автором")
class AuthorDaoJdbcTest {

    final private static int AUTHORS_DB_COUNT = 11;
    final private static String AUTHOR_ID_2 = "Роджер Желязны";
    final private static String TEST_AUTHOR_NAME = "Test Author";
    final private static String TEST_AUTHOR_NAME_REN = "Test Author Renamed";
    @Autowired
    private AuthorDaoJdbc authorDao;


    @Test
    @DisplayName("getById получает нужный экземпляр по id")
    void getById() {
        Assert.assertEquals(authorDao.getById(2).getAuthorName(), AUTHOR_ID_2);
    }

    @Test
    @DisplayName("getByName получает нужный экземпляр по имени")
    void getByName() {
        Assert.assertEquals(authorDao.getByName(AUTHOR_ID_2).getId(), 2);
    }

    @Test
    @DisplayName("getAll получает всех авторов")
    void getAll() {
        Assert.assertEquals(authorDao.getAll().size(), AUTHORS_DB_COUNT);
    }


    @Test
    @DisplayName("insert добавляет автора в базу ")
    void insert() {
        Author testAuthor = new Author(TEST_AUTHOR_NAME);
        authorDao.insert(testAuthor);
        Assert.assertSame(testAuthor.getAuthorName(), authorDao.getByName(TEST_AUTHOR_NAME).getAuthorName());
    }

    @Test
    @DisplayName("update обновлет имя автора")
    void update() {
        Author testAuthor = new Author(TEST_AUTHOR_NAME);
        authorDao.insert(testAuthor);
        testAuthor = authorDao.getByName(TEST_AUTHOR_NAME);
        int expId = testAuthor.getId();
        authorDao.update(testAuthor, TEST_AUTHOR_NAME_REN);
        Author newTestAuthor = authorDao.getById(expId);
        Assert.assertSame(testAuthor.getId(), newTestAuthor.getId());
        Assert.assertNotSame(testAuthor.getAuthorName(), newTestAuthor.getAuthorName());
    }
}
