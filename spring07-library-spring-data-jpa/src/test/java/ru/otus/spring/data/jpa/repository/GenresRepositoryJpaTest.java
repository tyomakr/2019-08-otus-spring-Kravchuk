package ru.otus.spring.data.jpa.repository;

import lombok.val;
import org.junit.Assert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.otus.spring.data.jpa.domain.Genre;

@DataJpaTest
@DisplayName("Репозиторий для работы с жанрами")
class GenresRepositoryJpaTest {

    private static final Long GENRE_2_ID = 2L;
    private static final Genre TEST_GENRE_2 = new Genre(2, "Детектив");
    private static final String TEST_GENRE_NAME_2 = "Детектив";


    @Autowired
    GenresRepository repo;

    @Test
    @DisplayName("успешно получает все жанры из базы")
    void shouldFindAllGenres() {
        val genres = repo.findAll();
        Assert.assertArrayEquals(genres.toArray(), fillTestGenresArray());
    }

    @Test
    @DisplayName("успешно получает жанр по его ID")
    void shouldFindGenreById() {
        val eGenre = repo.findById(GENRE_2_ID).get();
        Assert.assertEquals(eGenre, TEST_GENRE_2);
    }

    @Test
    @DisplayName("успешно получает жанр по его имени")
    void shouldFindGenreByName() {
        Genre genre = repo.findGenreByGenreName(TEST_GENRE_NAME_2).orElse(null);
        Assert.assertEquals(genre, TEST_GENRE_2);
    }

    @Test
    @DisplayName("сохраняет жанр в БД")
    void shouldSaveGenre() {
        Genre genre = new Genre(TEST_GENRE_NAME_2 + " Test");
        repo.save(genre);
        Genre aGenre = repo.findGenreByGenreName(TEST_GENRE_NAME_2 + " Test").orElse(null);
        Assert.assertEquals(genre, aGenre);
    }


    private Genre[] fillTestGenresArray() {

        Genre a1 = new Genre(1,"Фантастика и фэнтези");
        Genre a2 = new Genre(2, "Детектив");
        Genre a3 = new Genre(3, "Мемуары");
        Genre a4 = new Genre(4, "Роман");
        Genre a5 = new Genre(5, "Поэма");
        Genre a6 = new Genre(6, "Техническая литература");
        Genre a7 = new Genre(7, "Программирование");

        return new Genre[]{a1, a2, a3, a4, a5, a6, a7};
    }
}