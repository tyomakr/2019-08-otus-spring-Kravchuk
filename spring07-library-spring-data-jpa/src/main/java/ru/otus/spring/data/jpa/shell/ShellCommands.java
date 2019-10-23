package ru.otus.spring.data.jpa.shell;

import lombok.RequiredArgsConstructor;
import org.h2.tools.Console;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.spring.data.jpa.services.*;

import java.sql.SQLException;

@ShellComponent
@RequiredArgsConstructor
public class ShellCommands {

    private final static String UNDEF = "undefined";
    private final IOService ioService;
    private final AuthorsService as;
    private final GenresService gs;
    private final BooksService bs;
    private final CommentsService cs;


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
    @ShellMethod(value = "Получить список всех книг", key = {"b1", "all-books", "bl"})
    private void getAllBooks() {
        bs.findAllBooks();
    }

    @ShellMethod(value = "Добавить книгу", key = {"b2", "add-book", "ab"})
    private void addBook(@ShellOption(defaultValue = UNDEF) String bookName,
                         @ShellOption(defaultValue = UNDEF) String authorName,
                         @ShellOption(defaultValue = UNDEF) String genreName) {

        if (!bookName.equalsIgnoreCase(UNDEF) && !authorName.equalsIgnoreCase(UNDEF) && !genreName.equalsIgnoreCase(UNDEF)) {
            bs.addBook(bookName, authorName, genreName);
        } else {
            ioService.printMsg("sh.err.undef.book");
        }
    }

    @ShellMethod(value = "Добавить автора в книгу", key = {"b3", "add-author-book", "aab"})
    private void addAuthorToBook(@ShellOption(defaultValue = UNDEF) String bookId, @ShellOption(defaultValue = UNDEF) String authorName) {
        if (bookId.matches("\\d+") && !authorName.equalsIgnoreCase(UNDEF)) {
            bs.addAuthorToBook(Long.parseLong(bookId), authorName);
        } else {
            ioService.printMsg("sh.err.undef.aab");
        }
    }

    @ShellMethod(value = "Добавить жанр в книгу", key = {"b4", "add-genre-book", "agb"})
    private void addGenreToBook(@ShellOption(defaultValue = UNDEF) String bookId, @ShellOption(defaultValue = UNDEF) String genreName) {
        if(bookId.matches("\\d+") && !genreName.equalsIgnoreCase(UNDEF)) {
            bs.addGenreToBook(Long.parseLong(bookId), genreName);
        } else {
            ioService.printMsg("sh.err.undef.agb");
        }
    }

    @ShellMethod(value = "Изменить название книги", key = {"b5", "edit-book-name", "ebn"})
    private void editBookName(@ShellOption(defaultValue = UNDEF) String bookId, @ShellOption(defaultValue = UNDEF) String newBookName) {
        if (bookId.matches("\\d+") && !newBookName.equalsIgnoreCase(UNDEF)) {
            bs.updateBookName(Long.parseLong(bookId), newBookName);
        } else {
            ioService.printMsg("sh.err.undef.ebn");
        }
    }

    @ShellMethod(value = "Изменить автора книги", key = {"b6", "edit-book-author", "eba"})
    private void editBookAuthor(@ShellOption(defaultValue = UNDEF) String bookId,
                                @ShellOption(defaultValue = UNDEF) String origBookAuthor,
                                @ShellOption(defaultValue = UNDEF) String changedBookAuthor) {
        if (bookId.matches("\\d+") && !origBookAuthor.equalsIgnoreCase(UNDEF) && !changedBookAuthor.equalsIgnoreCase(UNDEF)) {
            bs.updateBookAuthor(Long.parseLong(bookId), origBookAuthor, changedBookAuthor);
        } else {
            ioService.printMsg("sh.err.undef.eba");
        }
    }

    @ShellMethod(value = "Изменить жанр книги", key = {"b7", "edit-book-genre", "ebg"})
    private void editBookGenre(@ShellOption(defaultValue = UNDEF) String bookId,
                               @ShellOption(defaultValue = UNDEF) String origBookGenre,
                               @ShellOption(defaultValue = UNDEF) String changedBookGenre) {
        if (bookId.matches("\\d+") && !origBookGenre.equalsIgnoreCase(UNDEF) && !changedBookGenre.equalsIgnoreCase(UNDEF)) {
            bs.updateBookGenre(Long.parseLong(bookId), origBookGenre, changedBookGenre);
        } else {
            ioService.printMsg("sh.err.undef.ebg");
        }
    }

    @ShellMethod(value = "Удалить книгу по ID", key = {"b8", "delete-book", "del"})
    private void deleteBook(@ShellOption(defaultValue = UNDEF) String bookId) {
        if (bookId.matches("\\d+")) {
            bs.deleteBook(Long.parseLong(bookId));
        } else {
            ioService.printMsg("sh.err.undef.del");
        }
    }

    @ShellMethod(value = "Удалить автора из книги", key = {"b9", "remove-author-book", "rab"})
    private void removeAuthorToBook(@ShellOption(defaultValue = UNDEF) String bookId, @ShellOption(defaultValue = UNDEF) String authorName) {
        if (bookId.matches("\\d+") && !authorName.equalsIgnoreCase(UNDEF)) {
            bs.removeAuthorToBook(Long.parseLong(bookId), authorName);
        } else {
            ioService.printMsg("sh.err.undef.rab");
        }
    }

    @ShellMethod(value = "Удалить жанр из книги", key = {"b10", "remove-genre-book", "rgb"})
    private void removeGenreToBook(@ShellOption(defaultValue = UNDEF) String bookId, @ShellOption(UNDEF) String genreName) {
        if (bookId.matches("\\d+") && !genreName.equalsIgnoreCase(UNDEF)) {
            bs.removeGenreToBook(Long.parseLong(bookId), genreName);
        } else {
            ioService.printMsg("sh.err.undef.rgb");
        }
    }


    /*AUTHORS COMMANDS*/

    @ShellMethod(value = "Получить список всех авторов", key = {"a1", "all-authors"})
    private void getAllAuthors() {
        as.findAllAuthors();
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
        gs.findAllGenres();
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


    /* COMMENTS COMMANDS */
    @ShellMethod(value = "Получить список комментариев к книге", key = {"c1", "bc", "book-comments"})
    private void getCommentsFromBook(@ShellOption(defaultValue = UNDEF) String bookId) {
        if(bookId.matches("\\d+")) {
            cs.findCommentsFromBook(Long.parseLong(bookId));
        } else {
            ioService.printMsg("sh.err.undef.com.book.id");
        }
    }
    @ShellMethod(value = "Оставить комментарий к книге", key = {"c2", "sc", "save-comment"})
    private void saveCommentFromBook(@ShellOption(defaultValue = UNDEF) String bookId, @ShellOption(defaultValue = UNDEF) String commentText) {
        if (bookId.matches("\\d+") && !commentText.equalsIgnoreCase(UNDEF)) {
            cs.setCommentFromBook(Long.parseLong(bookId), commentText);
        } else {
            ioService.printMsg("sh.err.undef.com.set.com");
        }
    }

    @ShellMethod(value = "Удалить комментарий к книге", key = {"c3", "dc", "del-com"})
    private void deleteComment(@ShellOption(defaultValue = UNDEF) String commentId) {
        if (commentId.matches("\\d+")) {
            cs.deleteComment(Long.parseLong(commentId));
        } else {
            ioService.printMsg("sh.err.undef.com.id");
        }
    }




}
