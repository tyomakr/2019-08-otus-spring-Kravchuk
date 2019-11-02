package ru.otus.spring.library.repository;

import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.ComponentScan;
import ru.otus.spring.library.model.Genre;

import static org.junit.jupiter.api.Assertions.*;

@DataMongoTest
@EnableConfigurationProperties
@ComponentScan("ru.otus.spring.library")
@DisplayName("GenreRepository должен")
class GenreRepositoryTest {

    @Autowired
    GenreRepository genreRepository;

    private static final Genre TEST_GENRE = new Genre("Детектив");
    private static final Genre TEST_GENRE_UCASE = new Genre("ДЕТЕКТИВ");
    private static final Genre NOT_EXISTS_GENRE = new Genre("NOT_EX_GENRE");

    @Test
    @DisplayName("возвращать жанр по его названию")
    void findGenreByGenreTitle() {
        val tGenre = genreRepository.findGenreByGenreTitle(TEST_GENRE.getGenreTitle());
        assertEquals(tGenre.get().getGenreTitle(), TEST_GENRE.getGenreTitle());
    }

    @Test
    @DisplayName("проверять, существует ли данный жанр в базе")
    void existsByGenreTitleEqualsIgnoreCase() {
        assertTrue(genreRepository.existsByGenreTitleEqualsIgnoreCase(TEST_GENRE.getGenreTitle()));
        assertTrue(genreRepository.existsByGenreTitleEqualsIgnoreCase(TEST_GENRE_UCASE.getGenreTitle()));
        assertFalse(genreRepository.existsByGenreTitleEqualsIgnoreCase(NOT_EXISTS_GENRE.getGenreTitle()));
    }
}