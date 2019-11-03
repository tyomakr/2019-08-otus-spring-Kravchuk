package ru.otus.spring.library.webmvc.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.spring.library.webmvc.domain.Genre;

import java.util.Optional;

public interface GenreRepository extends MongoRepository<Genre, String> {

    Optional<Genre> findGenreByGenreTitle(String genreTitle);

    boolean existsByGenreTitleEqualsIgnoreCase(String genreTitle);
}