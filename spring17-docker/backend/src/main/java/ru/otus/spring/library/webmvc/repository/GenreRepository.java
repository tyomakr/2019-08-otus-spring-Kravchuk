package ru.otus.spring.library.webmvc.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import ru.otus.spring.library.webmvc.domain.Author;
import ru.otus.spring.library.webmvc.domain.Genre;

import java.util.List;
import java.util.Optional;

@RepositoryRestResource(path = "genres")
public interface GenreRepository extends MongoRepository<Genre, String> {

    @RestResource(path = "genre-names", rel = "author-names")
    Optional<Genre> findGenreByGenreTitle(String genreTitle);
}