package ru.otus.spring.library.springjdbc.dao.impl;

import org.junit.Assert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.spring.library.springjdbc.domain.Author;

import java.util.*;


@ExtendWith(SpringExtension.class)
@JdbcTest
@Import(AuthorDaoJdbc.class)
@DisplayName("DAO для работы с автором")
class AuthorDaoJdbcTest {

    final private static long TEST_AUTHOR_ID_2 = 2;
    final private static String TEST_AUTHOR_NAME = "Test Author";
    final private static String TEST_AUTHOR_NAME_2 = "Роджер Желязны";
    final private static String TEST_AUTHOR_NAME_REN = "Test Author Renamed";


    @Autowired
    private AuthorDaoJdbc authorDao;

    @Test
    @DisplayName("getById получает нужный экземпляр по id")
    void getById() {
        Assert.assertEquals(authorDao.getById(TEST_AUTHOR_ID_2).getAuthorName(), TEST_AUTHOR_NAME_2);
    }

    @Test
    @DisplayName("getByName получает нужный экземпляр по имени")
    void getByName() {
        Assert.assertEquals(authorDao.getByName(TEST_AUTHOR_NAME_2).getId(), TEST_AUTHOR_ID_2);
    }

    @Test
    @DisplayName("getAll получает всех авторов")
    void getAll() {
        List<Author> daoAuthorsList = authorDao.getAll();
        Assert.assertArrayEquals(daoAuthorsList.toArray(), fillTestAuthorsArray());
    }

    @Test
    @DisplayName("insert добавляет автора в базу ")
    void insert() {
        Author testAuthor = new Author(TEST_AUTHOR_NAME);
        authorDao.insert(testAuthor);
        Assert.assertEquals(testAuthor.getAuthorName(), authorDao.getByName(TEST_AUTHOR_NAME).getAuthorName());
    }

    @Test
    @DisplayName("update обновлет имя автора")
    void update() {
        Author testAuthor = new Author(TEST_AUTHOR_NAME);
        authorDao.insert(testAuthor);
        testAuthor = authorDao.getByName(TEST_AUTHOR_NAME);
        long expId = testAuthor.getId();
        authorDao.update(testAuthor, TEST_AUTHOR_NAME_REN);
        Author newTestAuthor = authorDao.getById(expId);
        Assert.assertSame(testAuthor.getId(), newTestAuthor.getId());
        Assert.assertNotSame(testAuthor.getAuthorName(), newTestAuthor.getAuthorName());
    }


    private Author[] fillTestAuthorsArray() {

        Author a1 = new Author(1,"Джон Рональд Руэл Толкин");
        Author a2 = new Author(2, "Роджер Желязны");
        Author a3 = new Author(3, "Теодор Драйзер");
        Author a4 = new Author(4, "Дэн Симмонс");
        Author a5 = new Author(5, "Джеральд Даррелл");
        Author a6 = new Author(6, "Федор Достоевский");
        Author a7 = new Author(7, "Агата Кристи");
        Author a8 = new Author(8, "Артур Конан Дойл");
        Author a9 = new Author(9, "Александр Дюма");
        Author a10 = new Author(10, "Анджей Сапковский");
        Author a11 = new Author(11, "Данте Алигьери");

        return new Author[]{a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11};
    }
}
