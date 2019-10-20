package ru.otus.spring.library.jpa.repository;

import ru.otus.spring.library.jpa.domain.Genre;

import java.util.List;
import java.util.Optional;

public interface GenresRepository {

    List<Genre> findAllGenres();

    Genre findGenreById(long id);

    Optional<Genre> findGenreByName(String genreName);

    Genre saveGenre(Genre genre);
}
