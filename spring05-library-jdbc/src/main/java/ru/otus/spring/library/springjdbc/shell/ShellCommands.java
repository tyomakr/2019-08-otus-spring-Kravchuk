package ru.otus.spring.library.springjdbc.shell;

import lombok.RequiredArgsConstructor;
import org.h2.tools.Console;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.spring.library.springjdbc.service.AuthorService;
import ru.otus.spring.library.springjdbc.service.BookService;
import ru.otus.spring.library.springjdbc.service.IOService;
import ru.otus.spring.library.springjdbc.service.GenreService;

import java.sql.SQLException;

@ShellComponent
@RequiredArgsConstructor
public class ShellCommands {

    private final BookService bs;
    private final AuthorService as;
    private final GenreService gs;
    private final MessageSource ms;
    private final IOService ioService;
    private static final String UNDEF = "UNDEFINED";

    @ShellMethod(value = "Показать консоль H2", key = {"c", "console"})
    public void showH2Console() {
        try {
            Console.main();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    /* BOOKS */
    @ShellMethod(value = "Список всех книг в библиотеке", key = {"1", "all-books", "get-books"})
    public void getAllBooks() {
        bs.getAllBooks();
    }

    @ShellMethod(value = "Добавить новую книгу в библиотеку", key = {"2", "add-book", "ab"})
    public void addBook(@ShellOption(defaultValue = UNDEF) String bookName,
                        @ShellOption(defaultValue = UNDEF) String authorName,
                        @ShellOption(defaultValue = UNDEF) String genreName) {
        if (!bookName.equalsIgnoreCase(UNDEF) && !authorName.equalsIgnoreCase(UNDEF) && !genreName.equalsIgnoreCase(UNDEF)) {
            bs.insertBook(bookName, authorName, genreName);
        } else {
            ioService.printMsg(ms.getMessage("sh.err.syn.book", null, LocaleContextHolder.getLocale()));
        }
    }

    @ShellMethod(value = "Редактировать название книги", key = {"3", "edit-book", "eb"})
    private void updateBook(@ShellOption(defaultValue = UNDEF) String oldBookName,
                            @ShellOption(defaultValue = UNDEF) String authorName,
                            @ShellOption(defaultValue = UNDEF) String newBookName){
        if (!oldBookName.equalsIgnoreCase(UNDEF) && !authorName.equalsIgnoreCase(UNDEF) && !newBookName.equalsIgnoreCase(UNDEF)) {
            bs.updateBook(oldBookName, authorName, newBookName);
        } else {
            ioService.printMsg(ms.getMessage("sh.err.syn.bupd", null, LocaleContextHolder.getLocale()));
        }
    }

    @ShellMethod(value = "Изменить автора книги", key = {"4", "edit-book-author", "eba"})
    private void updateBookAuthor(@ShellOption(defaultValue = UNDEF) String bookName,
                                  @ShellOption(defaultValue = UNDEF) String bookOldAuthor,
                                  @ShellOption(defaultValue = UNDEF) String bookNewAuthor) {
        if (!bookName.equalsIgnoreCase(UNDEF) && !bookOldAuthor.equalsIgnoreCase(UNDEF) && !bookNewAuthor.equalsIgnoreCase(UNDEF)) {
            bs.updateBookAuthor(bookName, bookOldAuthor, bookNewAuthor);
        } else {
            ioService.printMsg(ms.getMessage("sh.err.syn.uba", null, LocaleContextHolder.getLocale()));
        }
    }

    @ShellMethod(value = "Изменить жанр книги", key = {"5", "edit-book-genre", "ebg"})
    private void updateBookGenre(@ShellOption(defaultValue = UNDEF) String bookName,
                                  @ShellOption(defaultValue = UNDEF) String bookAuthor,
                                  @ShellOption(defaultValue = UNDEF) String bookNewGenre) {
        if (!bookName.equalsIgnoreCase(UNDEF) && !bookAuthor.equalsIgnoreCase(UNDEF) && !bookNewGenre.equalsIgnoreCase(UNDEF)) {
            bs.updateBookGenre(bookName, bookAuthor, bookNewGenre);
        } else {
            ioService.printMsg(ms.getMessage("sh.err.syn.ubg", null, LocaleContextHolder.getLocale()));
        }
    }

    @ShellMethod(value = "Удалить книгу", key = {"6", "del-book", "delete"})
    private void deleteBook(@ShellOption(defaultValue = UNDEF) String bookId) {
        if(!bookId.equalsIgnoreCase(UNDEF) ) {
            bs.deleteBook(bookId);
        } else {
            ioService.printMsg(ms.getMessage("sh.err.syn.bdel", null, LocaleContextHolder.getLocale()));
        }
    }


    /* AUTHORS */
    @ShellMethod(value = "Список всех авторов", key = {"7", "all-authors", "get-authors"})
    public void getAllAuthors() {
        as.getAllAuthors();
    }

    @ShellMethod(value = "Добавить нового автора", key = {"8", "add-author", "aa"})
    public void addAuthor(@ShellOption(defaultValue = UNDEF) String authorName) {
        if (!authorName.equalsIgnoreCase(UNDEF)) {
            as.addAuthor(authorName);
        }
        else {
            ioService.printMsg(ms.getMessage("sh.err.syn.author", null, LocaleContextHolder.getLocale()));
        }
    }
    @ShellMethod(value = "Изменить имя автора", key = {"9", "edit-author", "ea"})
    public void updateAuthor(@ShellOption(defaultValue = UNDEF) String oldAuthorName,
                             @ShellOption(defaultValue = UNDEF) String newAuthorName) {
        if (!oldAuthorName.equalsIgnoreCase(UNDEF) && !newAuthorName.equalsIgnoreCase(UNDEF)) {
            as.updateAuthor(oldAuthorName, newAuthorName);
        }
        else {
            ioService.printMsg(ms.getMessage("sh.err.syn.auupd", null, LocaleContextHolder.getLocale()));
        }
    }


    /* GENRES */
    @ShellMethod(value = "Список всех жанров", key = {"10", "all-genres", "get-genres"})
    public void getAllGenres() {
        gs.getAllGenres();
    }

    @ShellMethod(value = "Добавить жанр", key = {"11", "add-genre", "ag"})
    public void addGenre(@ShellOption(defaultValue = "Undefined") String genreName) {
        if (!genreName.equalsIgnoreCase("Undefined")) {
            gs.addGenre(genreName);
        }
        else {
            ioService.printMsg(ms.getMessage("sh.err.syn.genre", null, LocaleContextHolder.getLocale()));
        }
    }

    @ShellMethod(value = "Изменить название жанра", key = {"12", "edit-genre", "eg"})
    public void updateGenre(@ShellOption(defaultValue = UNDEF) String oldGenreName,
                             @ShellOption(defaultValue = UNDEF) String newGenreName) {
        if (!oldGenreName.equalsIgnoreCase(UNDEF) && !newGenreName.equalsIgnoreCase(UNDEF)) {
            gs.updateGenre(oldGenreName, newGenreName);
        }
        else {
            ioService.printMsg(ms.getMessage("sh.err.syn.auupd", null, LocaleContextHolder.getLocale()));
        }
    }
}
