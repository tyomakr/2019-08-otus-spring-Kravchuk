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

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataMongoTest
@EnableConfigurationProperties
@ComponentScan("ru.otus.spring.library.repository.AuthorRepository")
@DisplayName("AuthorRepository должен")
class AuthorRepositoryTest {

    @Autowired
    AuthorRepository authorRepository;

    private static final Author TEST_AUTHOR = new Author("TEST AUTHOR");
    private static final Author TEST_AUTHOR_LCASE = new Author("test author");
    private static final Author NOT_EXISTING_AUTHOR = new Author("NOT_EX_AUTHOR");

    @Test
    @DisplayName("возвращать автора по его имени")
    void findAuthorByAuthorName() {
        authorRepository.save(TEST_AUTHOR);
        val tAuthor = authorRepository.findAuthorByAuthorName(TEST_AUTHOR.getAuthorName());
        assertThat(tAuthor).isEqualTo(Optional.of(TEST_AUTHOR));
        assertThat(tAuthor).contains(TEST_AUTHOR);
    }

    @Test
    @DisplayName("проверять, существует ли такой автор в базе")
    void existsByAuthorNameEqualsIgnoreCase() {
        assertTrue(authorRepository.existsByAuthorNameEqualsIgnoreCase(TEST_AUTHOR.getAuthorName()));
        assertTrue(authorRepository.existsByAuthorNameEqualsIgnoreCase(TEST_AUTHOR_LCASE.getAuthorName()));
        assertFalse(authorRepository.existsByAuthorNameEqualsIgnoreCase(NOT_EXISTING_AUTHOR.getAuthorName()));
    }
}