package ru.otus.spring.library.jpa.shell;

import lombok.RequiredArgsConstructor;
import org.h2.tools.Console;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.spring.library.jpa.services.AuthorsService;
import ru.otus.spring.library.jpa.services.BooksService;
import ru.otus.spring.library.jpa.services.GenresService;
import ru.otus.spring.library.jpa.services.IOService;

import java.sql.SQLException;

@ShellComponent
@RequiredArgsConstructor
public class ShellCommands {

    private final static String UNDEF = "undefined";
    private final IOService ioService;
    private final AuthorsService as;
    private final GenresService gs;
    private final BooksService bs;


    /*CONSOLE*/
    @ShellMethod(value = "Показать консоль H2", key = {"h2", "c", "console"})
    private void showH2Console() {
        try {
            Console.main();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    /*BOOKS COMMANDS */
    @ShellMethod(value = "Получить список всех книг", key = {"b1", "all-books"})
    private void getAllBooks() {
        bs.findAll();
    }


    /*AUTHORS COMMANDS*/

    @ShellMethod(value = "Получить список всех авторов", key = {"a1", "all-authors"})
    private void getAllAuthors() {
        as.findAll();
    }

    @ShellMethod(value = "Добавить автора", key = {"a2", "add-author"})
    private void insertAuthor(@ShellOption(defaultValue = UNDEF) String authorName) {
        if (!authorName.equalsIgnoreCase(UNDEF)) {
            as.insertAuthor(authorName);
        } else {
            ioService.printMsg("sh.err.undef.author");
        }
    }

    @ShellMethod(value = "Изменить имя автора во всех книгах", key = {"a3", "edit-author"})
    private void updateAuthor(@ShellOption(defaultValue = UNDEF) String originalAuthorName,
                              @ShellOption(defaultValue = UNDEF) String changedAuthorName) {
        if (!originalAuthorName.equalsIgnoreCase(UNDEF) && !changedAuthorName.equalsIgnoreCase(UNDEF)) {
            as.updateAuthorName(originalAuthorName, changedAuthorName);
        } else {
            ioService.printMsg("sh.err.undef.upd.authors");
        }
    }


    /*GENRES COMMANDS */

    @ShellMethod(value = "Получить список всех жанров", key = {"g1", "all-genres"})
    private void getAllGenres() {
        gs.findAll();
    }

    @ShellMethod(value = "Добавить жанр", key = {"g2", "add-genre"})
    private void insertGenre(@ShellOption(defaultValue = UNDEF) String genreName) {
        if (!genreName.equalsIgnoreCase(UNDEF)) {
            gs.insertGenre(genreName);
        } else {
            ioService.printMsg("sh.err.undef.genre");
        }
    }

    @ShellMethod(value = "Изменить имя жанра во всех книгах", key = {"g3", "edit-genre"})
    private void updateGenre(@ShellOption(defaultValue = UNDEF) String originalGenreName,
                              @ShellOption(defaultValue = UNDEF) String changedGenreName) {
        if (!originalGenreName.equalsIgnoreCase(UNDEF) && !changedGenreName.equalsIgnoreCase(UNDEF)) {
            gs.updateGenreName(originalGenreName, changedGenreName);
        } else {
            ioService.printMsg("sh.err.undef.upd.genres");
        }
    }




}
