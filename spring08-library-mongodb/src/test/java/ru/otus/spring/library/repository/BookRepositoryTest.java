package ru.otus.spring.library.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.ComponentScan;
import ru.otus.spring.library.model.Book;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataMongoTest
@EnableConfigurationProperties
@ComponentScan("ru.otus.spring.library.repository.BookRepository, ru.otus.spring.library.config")
@DisplayName("BookRepository должен")
class BookRepositoryTest {

    @Autowired
    BookRepository bookRepository;

    private static final String TEST_BOOK_TITLE = "Властелин колец";

    @Test
    @DisplayName("получить книгу по ее названию")
    void findByTitle() {

        Optional<Book> testBook = bookRepository.findByTitle(TEST_BOOK_TITLE);

        assertEquals(testBook.get().getTitle(), TEST_BOOK_TITLE);
    }
}