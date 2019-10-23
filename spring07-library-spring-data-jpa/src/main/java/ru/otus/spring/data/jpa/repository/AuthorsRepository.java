package ru.otus.spring.data.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.spring.data.jpa.domain.Author;

import java.util.Optional;

public interface AuthorsRepository extends JpaRepository<Author, Long> {

    Optional<Author> findAuthorByAuthorName(String authorName);
}
