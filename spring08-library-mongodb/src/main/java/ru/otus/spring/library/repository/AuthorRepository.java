package ru.otus.spring.library.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.spring.library.model.Author;

import java.util.Optional;

public interface AuthorRepository extends MongoRepository<Author, String> {

    Optional<Author> findAuthorByAuthorName(String authorName);

    boolean existsByAuthorNameEqualsIgnoreCase(String authorName);
}