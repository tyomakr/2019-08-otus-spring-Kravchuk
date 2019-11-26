package ru.otus.spring.library.webmvc.service;

import ru.otus.spring.library.webmvc.domain.Genre;

public interface GenreService {

    Genre findOrCreateGenre(String bookGenre);
}
