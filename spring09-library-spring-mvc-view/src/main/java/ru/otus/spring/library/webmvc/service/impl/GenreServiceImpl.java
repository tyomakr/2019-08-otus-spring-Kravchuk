package ru.otus.spring.library.webmvc.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.library.webmvc.domain.Genre;
import ru.otus.spring.library.webmvc.repository.GenreRepository;
import ru.otus.spring.library.webmvc.service.GenreService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {

    private final GenreRepository genreRepository;

    @Override
    public Optional<Genre> findById(String bookGenre) {
        return genreRepository.findById(bookGenre);
    }

    @Override
    public List<Genre> findAll() {
        return genreRepository.findAll();
    }


    @Override
    public Genre findOrCreateGenre(String bookGenre) {
        Optional<Genre> genreOptional = genreRepository.findGenreByGenreTitle(bookGenre);
        return genreRepository.save(genreOptional.orElseGet(() -> new Genre(bookGenre)));
    }
}
