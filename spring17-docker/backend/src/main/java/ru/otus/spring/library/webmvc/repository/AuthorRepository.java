package ru.otus.spring.library.webmvc.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import ru.otus.spring.library.webmvc.domain.Author;

import java.util.List;
import java.util.Optional;

@RepositoryRestResource(path = "authors")
public interface AuthorRepository extends MongoRepository<Author, String> {

    @RestResource(path = "author-names", rel = "author-names")
    Optional<Author> findAuthorByAuthorName(String authorName);
}