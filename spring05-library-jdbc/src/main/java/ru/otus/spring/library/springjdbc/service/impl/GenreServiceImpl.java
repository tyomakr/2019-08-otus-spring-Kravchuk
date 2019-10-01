package ru.otus.spring.library.springjdbc.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import ru.otus.spring.library.springjdbc.dao.GenreDao;
import ru.otus.spring.library.springjdbc.domain.Genre;
import ru.otus.spring.library.springjdbc.service.GenreService;
import ru.otus.spring.library.springjdbc.service.IOService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {

    private final GenreDao genreDao;
    private final MessageSource ms;
    private final IOService ioService;


    @Override
    public void getAllGenres() {
        ioService.printMsg(ms.getMessage("gs.str.get.all", null, LocaleContextHolder.getLocale()));
        List<Genre> genreList = genreDao.getAll();
        for (Genre genre : genreList) {
            ioService.printMsg(genre.getId() + ". " +genre.getGenreName());
        }
    }

    @Override
    public void addGenre(String genreName) {
        Genre genre = new Genre(genreName);
        if (!isGenreExists(genre)) {
            genreDao.insert(genre);
            ioService.printMsg(ms.getMessage("gs.str.added", null, LocaleContextHolder.getLocale()));
        } else {
            ioService.printMsg(ms.getMessage("gs.err.exists", null, LocaleContextHolder.getLocale()));
        }
    }

    @Override
    public void updateGenre(String oldGenreName, String newGenreName) {
        Genre oldGenre = new Genre(oldGenreName);
        if (isGenreExists(oldGenre)) {
            oldGenre = genreDao.getByName(oldGenreName);
            genreDao.update(oldGenre, newGenreName);
            ioService.printMsg(ms.getMessage("gs.str.edited", null, LocaleContextHolder.getLocale()));
        } else {
            ioService.printMsg(ms.getMessage("gs.err.not.found", null, LocaleContextHolder.getLocale()));
        }
    }

    @Override
    public boolean isGenreExists(Genre genre) {
        List<Genre> genreList = genreDao.getAll();
        for (Genre value : genreList) {
            if (value.getGenreName().equalsIgnoreCase(genre.getGenreName())) {
                return true;
            }
        }
        return false;
    }
}
