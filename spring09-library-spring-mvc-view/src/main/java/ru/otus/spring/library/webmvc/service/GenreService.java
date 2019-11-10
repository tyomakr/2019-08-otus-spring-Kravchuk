package ru.otus.spring.library.webmvc.service;

import ru.otus.spring.library.webmvc.domain.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreService {

    Optional<Genre> findById(String bookGenre);

    List<Genre> findAll();

    Genre findOrCreateGenre(String bookGenre);
}
