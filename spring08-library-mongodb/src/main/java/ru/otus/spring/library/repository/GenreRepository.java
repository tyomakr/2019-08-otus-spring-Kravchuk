package ru.otus.spring.library.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.spring.library.model.Genre;

import java.util.Optional;

public interface GenreRepository extends MongoRepository<Genre, String> {

    Optional<Genre> findGenreByGenreTitle(String genreTitle);

    boolean existsByGenreTitleEqualsIgnoreCase(String genreTitle);
}
