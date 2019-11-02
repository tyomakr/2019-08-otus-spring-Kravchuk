package ru.otus.spring.library.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.spring.library.services.*;

@ShellComponent
@RequiredArgsConstructor
public class ShellCommands {

    private final BookService bs;
    private final AuthorService as;
    private final GenreService gs;
    private final CommentService cs;
    private final IOService ioService;

    private static final String UNDEF = "UNDEFINED";


    /* BOOKS */
    @ShellMethod(value = "Получить список всех книг", key = {"b1", "all-books", "bl"})
    private void getAllBooks() {
        bs.findAll();
    }

    @ShellMethod(value = "Добавить книгу", key = {"b2", "add-book", "ab"})
    private void insertBook(@ShellOption(defaultValue = UNDEF) String bookTitle,
                            @ShellOption(defaultValue = UNDEF) String bookAuthor,
                            @ShellOption(defaultValue = UNDEF) String bookGenre) {
        if(!bookTitle.equalsIgnoreCase(UNDEF) && !bookAuthor.equalsIgnoreCase(UNDEF) && !bookGenre.equalsIgnoreCase(UNDEF)) {
            bs.insertBook(bookTitle, bookAuthor, bookGenre);
        } else {
            ioService.printMsg("sh.err.ba.undef");
        }
    }

    @ShellMethod(value = "Обновить название книги", key = {"b3", "update-book-title", "ubt"})
    private void updateBookTitle(@ShellOption(defaultValue = UNDEF) String bookId, @ShellOption(defaultValue = UNDEF) String newBookName) {
        if (!bookId.equalsIgnoreCase(UNDEF) && !newBookName.equalsIgnoreCase(UNDEF)) {
            bs.updateBookName(bookId, newBookName);
        } else {
            ioService.printMsg("sh.err.bu.undef");
        }
    }

    @ShellMethod(value = "Обновить имя автора в книге", key = {"b4", "update-book-author", "uba"})
    private void updateBookAuthor(@ShellOption(defaultValue = UNDEF) String bookId,
                                  @ShellOption(defaultValue = UNDEF) String oldBookAuthor,
                                  @ShellOption(defaultValue = UNDEF) String newBookAuthor) {
        if (!bookId.equalsIgnoreCase(UNDEF) && !oldBookAuthor.equalsIgnoreCase(UNDEF) && !newBookAuthor.equalsIgnoreCase(UNDEF)) {
            bs.updateBookAuthor(bookId, oldBookAuthor, newBookAuthor);
        } else {
            ioService.printMsg("sh.err.uba.undef");
        }
    }

    @ShellMethod(value = "Обновить название жанра в книге", key = {"b5", "update-book-genre", "ubg"})
    private void updateBookGenre(@ShellOption(defaultValue = UNDEF) String bookId,
                                  @ShellOption(defaultValue = UNDEF) String oldBookGenre,
                                  @ShellOption(defaultValue = UNDEF) String newBookGenre) {
        if (!bookId.equalsIgnoreCase(UNDEF) && !oldBookGenre.equalsIgnoreCase(UNDEF) && !newBookGenre.equalsIgnoreCase(UNDEF)) {
            bs.updateBookGenre(bookId, oldBookGenre, newBookGenre);
        } else {
            ioService.printMsg("sh.err.ubg.undef");
        }
    }

    @ShellMethod(value = "Добавить автора в книгу", key = {"b6", "add-book-author", "aba"})
    private void addBookAuthor(@ShellOption(defaultValue = UNDEF) String bookId, @ShellOption(defaultValue = UNDEF) String bookAuthor) {
        if (!bookId.equalsIgnoreCase(UNDEF) && !bookAuthor.equalsIgnoreCase(UNDEF)) {
            bs.addBookAuthor(bookId, bookAuthor);
        } else {
            ioService.printMsg("sh.err.aba.undef");
        }
    }

    @ShellMethod(value = "Удалить автора из книги", key = {"b7", "remove-book-author", "rba"})
    private void removeBookAuthor(@ShellOption(defaultValue = UNDEF) String bookId, @ShellOption(defaultValue = UNDEF) String bookAuthor) {
        if (!bookId.equalsIgnoreCase(UNDEF) && !bookAuthor.equalsIgnoreCase(UNDEF)) {
            bs.removeBookAuthor(bookId, bookAuthor);
        } else {
            ioService.printMsg("sh.err.rba.undef");
        }
    }

    @ShellMethod(value = "Добавить жанр в книгу", key = {"b8", "add-book-genre", "abg"})
    private void addBookGenre(@ShellOption(defaultValue = UNDEF) String bookId, @ShellOption(defaultValue = UNDEF) String bookGenre) {
        if (!bookId.equalsIgnoreCase(UNDEF) && !bookGenre.equalsIgnoreCase(UNDEF)) {
            bs.addBookGenre(bookId, bookGenre);
        } else {
            ioService.printMsg("sh.err.abg.undef");
        }
    }

    @ShellMethod(value = "Удалить жанр из книги", key = {"b9", "remove-book-genre", "rbg"})
    private void removeBookGenre(@ShellOption(defaultValue = UNDEF) String bookId, @ShellOption(defaultValue = UNDEF) String bookGenre) {
        if (!bookId.equalsIgnoreCase(UNDEF) && !bookGenre.equalsIgnoreCase(UNDEF)) {
            bs.removeBookGenre(bookId, bookGenre);
        } else {
            ioService.printMsg("sh.err.rbg.undef");
        }
    }

    @ShellMethod(value = "Удалить книгу", key = {"b10", "remove-book", "rb"})
    private void removeBook(@ShellOption(defaultValue = UNDEF) String bookId) {
        if (!bookId.equalsIgnoreCase(UNDEF)) {
            bs.removeBook(bookId);
        } else {
            ioService.printMsg("sh.err.rb.undef");
        }
    }


    /* AUTHORS */
    @ShellMethod(value = "Получить список всех авторов", key = {"a1", "all-authors", "al"})
    private void getAllAuthors() {
        as.findAll();
    }

    @ShellMethod(value = "Добавить автора", key = {"a2", "add-author", "aa"})
    private void insertAuthor(@ShellOption(defaultValue = UNDEF) String bookAuthor) {
        if (!bookAuthor.equalsIgnoreCase(UNDEF)) {
            as.insertAuthor(bookAuthor);
        } else {
            ioService.printMsg("sh.err.aa.undef");
        }
    }

    @ShellMethod(value = "Обновить выбранного автора во всех книгах", key = {"a3", "update-author", "au"})
    private void updateAuthor(@ShellOption(defaultValue = UNDEF) String bookOldAuthor, @ShellOption(defaultValue = UNDEF) String bookNewAuthor) {
        if (!bookOldAuthor.equalsIgnoreCase(UNDEF) && !bookNewAuthor.equalsIgnoreCase(UNDEF)) {
            as.updateAuthor(bookOldAuthor, bookNewAuthor);
        } else {
            ioService.printMsg("sh.err.au.undef");
        }
    }


    /* GENRES */
    @ShellMethod(value = "Получить список всех жанров", key = {"g1", "all-genres", "gl"})
    private void getAllGenres() {
        gs.findAll();
    }

    @ShellMethod(value = "Добавить жанр", key = {"g2", "add-genre", "ag"})
    private void insertGenre(@ShellOption(defaultValue = UNDEF) String bookGenre) {
        if (!bookGenre.equalsIgnoreCase(UNDEF)) {
            gs.insertGenre(bookGenre);
        } else {
            ioService.printMsg("sh.err.ag.undef");
        }
    }

    @ShellMethod(value = "Обновить выбранный жанр во всех книгах", key = {"g3", "update-genre", "gu"})
    private void updateGenre(@ShellOption(defaultValue = UNDEF) String bookOldGenre, @ShellOption(defaultValue = UNDEF) String bookNewGenre) {
        if (!bookOldGenre.equalsIgnoreCase(UNDEF) && !bookNewGenre.equalsIgnoreCase(UNDEF)) {
            gs.updateGenre(bookOldGenre, bookNewGenre);
        } else {
            ioService.printMsg("sh.err.gu.undef");
        }
    }


    /* COMMENTS */
    @ShellMethod(value = "Получить список комментариев к книге по ID книги", key = {"c1", "find-comments", "fc"})
    private void findCommentsInBookByBookId(@ShellOption(defaultValue = UNDEF) String bookId) {
        if (!bookId.equalsIgnoreCase(UNDEF)) {
            cs.findAndShowCommentsByBookId(bookId);
        } else {
            ioService.printMsg("sh.err.fc.undef");
        }
    }

    @ShellMethod(value = "Добавить комментарий к книге по ID книги", key = {"c2", "add-comment", "ac"})
    private void addCommentInBookByBookId(@ShellOption(defaultValue = UNDEF) String bookId, @ShellOption(defaultValue = UNDEF) String commentText) {
        if (!bookId.equalsIgnoreCase(UNDEF) && !commentText.equalsIgnoreCase(UNDEF)) {
            cs.addCommentToBookByBookId(bookId, commentText);
        } else {
            ioService.printMsg("sh.err.ac.undef");
        }
    }

    @ShellMethod(value = "Удалить комментарий из книги по его ID", key = {"c3", "del-comment", "dc"})
    private void deleteCommentById(@ShellOption(defaultValue = UNDEF) String commentId) {
        if (!commentId.equalsIgnoreCase(UNDEF)) {
            cs.deleteCommentById(commentId);
        } else {
            ioService.printMsg("sh.err.dc.undef");
        }
    }



}
