package ru.otus.spring.library.jpa.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.library.jpa.domain.Genre;
import ru.otus.spring.library.jpa.repository.GenresRepository;
import ru.otus.spring.library.jpa.services.GenresService;
import ru.otus.spring.library.jpa.services.IOService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GenresServiceImpl implements GenresService {

    private final GenresRepository genresRepo;
    private final IOService ioService;

    @Override
    public void findAllGenres() {
        List<Genre> genreList = genresRepo.findAllGenres();
        ioService.printMsg("gs.msg.list");
        for (Genre genre : genreList) {
            ioService.printItemsList("%-4s %-50s %n", genre.getGenreId(), genre.getGenreName());
        }
    }

    @Override
    public void insertGenre(String genreName) {
        genresRepo.saveGenre(new Genre(genreName));
    }

    @Override
    public void updateGenreName(String originalGenreName, String changedGenreName) {
        try {
            Genre genre = getGenreByName(originalGenreName);
            genre.setGenreName(changedGenreName);
            genresRepo.saveGenre(genre);
        } catch (Exception e) {
            ioService.printMsg("gs.err.not.exists");
        }
    }

    @Override
    public Genre getGenreByName(String genreName) {
        Optional<Genre> optGenre = genresRepo.findGenreByName(genreName);
        return optGenre.orElseGet(() -> genresRepo.saveGenre(new Genre(genreName)));
    }
}
