package ru.otus.spring.library.webmvc.service;

import ru.otus.spring.library.webmvc.domain.Genre;

import java.util.List;

public interface GenreService {

    List<Genre> findAll();
}
