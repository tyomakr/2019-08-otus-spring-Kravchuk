package ru.otus.spring.data.jpa.repository;

import lombok.val;
import org.junit.Assert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.otus.spring.data.jpa.domain.Author;

@DataJpaTest
@DisplayName("Репозиторий для работы с авторами")
class AuthorsRepositoryTest {

    private static final Long AUTHOR_4_ID = 4L;
    private static final Author TEST_AUTHOR_4 = new Author(4, "Дэн Симмонс");
    private static final String TEST_AUTHOR_NAME_4 = "Дэн Симмонс";


    @Autowired
    AuthorsRepository repo;

    @Test
    @DisplayName("успешно получает всех авторов из базы")
    void shouldFindAllAuthors() {
        val authors = repo.findAll();
        Assert.assertArrayEquals(authors.toArray(), fillTestAuthorsArray());
    }

    @Test
    @DisplayName("успешно получает автора по его ID")
    void shouldFindAuthorById() {
        val eAuthor = repo.findById(AUTHOR_4_ID).get();
        Assert.assertEquals(eAuthor, TEST_AUTHOR_4);
    }

    @Test
    @DisplayName("успешно получает автора по его имени")
    void shouldFindAuthorByName() {
        Author author = repo.findAuthorByAuthorName(TEST_AUTHOR_NAME_4).orElse(null);
        Assert.assertEquals(author, TEST_AUTHOR_4);
    }

    @Test
    @DisplayName("сохраняет автора в БД")
    void shouldSaveAuthor() {
        Author author = new Author(TEST_AUTHOR_NAME_4 + " Test");
        repo.save(author);
        Author aAuthor = repo.findAuthorByAuthorName(TEST_AUTHOR_NAME_4 + " Test").orElse(null);
        Assert.assertEquals(author, aAuthor);
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
        Author a12 = new Author(12,"Кэти Сьерра");
        Author a13 = new Author(13,"Берт Бейтс");

        return new Author[]{a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13};
    }
}