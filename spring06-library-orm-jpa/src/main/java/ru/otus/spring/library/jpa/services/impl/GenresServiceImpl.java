package ru.otus.spring.library.jpa.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.library.jpa.domain.Genre;
import ru.otus.spring.library.jpa.repository.GenresRepository;
import ru.otus.spring.library.jpa.services.GenresService;
import ru.otus.spring.library.jpa.services.IOService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GenresServiceImpl implements GenresService {

    private final GenresRepository genresRepo;
    private final IOService ioService;

    @Override
    public void findAll() {
        List<Genre> genreList = genresRepo.findAllGenres();
        ioService.printMsg("gs.msg.list");
        for (Genre genre : genreList) {
            ioService.printItemsList("%-4s %-50s %n", genre.getGenreId(), genre.getGenreName());
        }
    }

    @Override
    public void insertGenre(String genreName) {
        if (!genresRepo.isExists(genreName)) {
            genresRepo.insertGenre(new Genre(genreName));
        } else {
            ioService.printMsg("gs.err.exists");
        }
    }

    @Override
    public void updateGenreName(String originalGenreName, String changedGenreName) {
        if (genresRepo.isExists(originalGenreName)) {
            Genre genre = genresRepo.findGenreByName(originalGenreName);
            genre.setGenreName(changedGenreName);
            genresRepo.updateGenre(genre);
        } else {
            ioService.printMsg("gs.err.not.exists");
        }
    }
}
