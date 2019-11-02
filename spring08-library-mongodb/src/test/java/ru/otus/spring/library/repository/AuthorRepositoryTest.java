package ru.otus.spring.library.repository;

import lombok.val;
import org.junit.Assert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.ComponentScan;
import ru.otus.spring.library.model.Author;

import static org.junit.jupiter.api.Assertions.*;

@DataMongoTest
@EnableConfigurationProperties
@ComponentScan("ru.otus.spring.library")
@DisplayName("AuthorRepository должен")
class AuthorRepositoryTest {

    @Autowired
    AuthorRepository authorRepository;

    private static final Author TEST_AUTHOR = new Author("Джон Рональд Руэл Толкин");
    private static final Author TEST_AUTHOR_LCASE = new Author("джон рональд руэл толкин");
    private static final Author NOT_EXISTING_AUTHOR = new Author("NOT_EX_AUTHOR");

    @Test
    @DisplayName("возвращать автора по его имени")
    void findAuthorByAuthorName() {
        val tAuthor = authorRepository.findAuthorByAuthorName(TEST_AUTHOR.getAuthorName());
        assertEquals(tAuthor.get().getAuthorName(), TEST_AUTHOR.getAuthorName());
    }

    @Test
    @DisplayName("проверять, существует ли такой автор в базе")
    void existsByAuthorNameEqualsIgnoreCase() {
        assertTrue(authorRepository.existsByAuthorNameEqualsIgnoreCase(TEST_AUTHOR.getAuthorName()));
        assertTrue(authorRepository.existsByAuthorNameEqualsIgnoreCase(TEST_AUTHOR_LCASE.getAuthorName()));
        assertFalse(authorRepository.existsByAuthorNameEqualsIgnoreCase(NOT_EXISTING_AUTHOR.getAuthorName()));
    }
}