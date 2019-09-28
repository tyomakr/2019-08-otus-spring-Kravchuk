package ru.otus.spring.library.springjdbc.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import ru.otus.spring.library.springjdbc.dao.AuthorDao;
import ru.otus.spring.library.springjdbc.dao.BookDao;
import ru.otus.spring.library.springjdbc.dao.GenreDao;
import ru.otus.spring.library.springjdbc.domain.Author;
import ru.otus.spring.library.springjdbc.domain.Book;
import ru.otus.spring.library.springjdbc.domain.Genre;
import ru.otus.spring.library.springjdbc.service.AuthorService;
import ru.otus.spring.library.springjdbc.service.BookService;
import ru.otus.spring.library.springjdbc.service.ConsoleService;
import ru.otus.spring.library.springjdbc.service.GenreService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookDao bookDao;
    private final AuthorService authorService;
    private final GenreService genreService;
    private final AuthorDao authorDao;
    private final GenreDao genreDao;
    private final MessageSource ms;
    private final ConsoleService cs = new ConsoleService();

    @Override
    public void getAllBooks() {
        List<Book> bookList = bookDao.getAll();
        cs.printMsg(ms.getMessage("bs.str.get.all", null, LocaleContextHolder.getLocale()));
        for (Book book : bookList) {
            cs.printItemsList("%-3s %-40s %-35s %-20s %n", book.getId(), book.getBookName(), book.getAuthor().getAuthorName(), book.getGenre().getGenreName());
        }
    }

    @Override
    public void insertBook(String bookName, String authorName, String genreName) {
        cs.printMsg(ms.getMessage("bs.str.add.att", null, LocaleContextHolder.getLocale()));
        Author author = new Author(authorName);
        Genre genre = new Genre(genreName);
        Book book = new Book(bookName, author, genre);

        cs.printMsg(ms.getMessage("bd.str.ins.check", null, LocaleContextHolder.getLocale()));
        if (!isBookExists(book)) {
            cs.printMsg(ms.getMessage("bs.str.ins.new", null, LocaleContextHolder.getLocale()));

            if (!authorService.isAuthorExists(book.getAuthor())) {
                authorDao.insert(book.getAuthor());
            }

            if(!genreService.isGenreExists(book.getGenre())) {
                genreDao.insert(book.getGenre());
            }

            bookDao.insert(book);
            cs.printMsg(ms.getMessage("bd.str.ins.done", null, LocaleContextHolder.getLocale()));

        } else {
            cs.printMsg(ms.getMessage("bs.str.ins.exist", null, LocaleContextHolder.getLocale()));
        }
    }

    @Override
    public void updateBook(String oldBookName, String authorName,  String newBookName) {
        try {
            Author author = authorDao.getByName(authorName);
            Book oldBook = bookDao.getByName(oldBookName,author.getId());
            bookDao.update(oldBook, newBookName);
            cs.printMsg(ms.getMessage("bs.str.ins.edited", null, LocaleContextHolder.getLocale()));
        } catch (Exception e) {
            cs.printMsg(ms.getMessage("bs.err.not.found", null, LocaleContextHolder.getLocale()));
        }
    }

    @Override
    public void deleteBook(String bookId) {
        try {
            int id = Integer.parseInt(bookId);
            Book book = bookDao.getById(id);

            cs.printMsg(ms.getMessage("bs.str.attention.del", new String[]{book.getBookName() + " / " + book.getAuthor().getAuthorName()}, LocaleContextHolder.getLocale()));
            bookDao.deleteById(id);
            cs.printMsg(ms.getMessage("bs.str.del", new String[]{book.getBookName() + " / " + book.getAuthor().getAuthorName()}, LocaleContextHolder.getLocale()));

        } catch (Exception e) {
            cs.printMsg(ms.getMessage("bs.err.input", null, LocaleContextHolder.getLocale()));
        }

    }

    @Override
    public boolean isBookExists(Book book) {
        List<Book> bookList = bookDao.getAll();
        for (Book value : bookList) {
            if (value.getBookName().equalsIgnoreCase(book.getBookName())) {
                if(value.getAuthor().getAuthorName().equalsIgnoreCase(book.getAuthor().getAuthorName()) ) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void updateBookAuthor(String bookName, String bookOldAuthor, String bookNewAuthor) {

        cs.printMsg(ms.getMessage("bs.msg.uba.att", null, LocaleContextHolder.getLocale()));

        try {
            Author oldAuthor = authorDao.getByName(bookOldAuthor);
            Author newAuthor = new Author(bookNewAuthor);
            if (!authorService.isAuthorExists(newAuthor)) {
                authorDao.insert(newAuthor);
            }
            newAuthor = authorDao.getByName(bookNewAuthor);

            Book book = bookDao.getByName(bookName, oldAuthor.getId());
            bookDao.updateBookAuthor(book, oldAuthor.getId(), newAuthor.getId());
            cs.printMsg(ms.getMessage("bs.msg.uba.done", null, LocaleContextHolder.getLocale()));

        } catch (Exception e) {
            cs.printMsg(ms.getMessage("bs.err.uba", null, LocaleContextHolder.getLocale()));
        }

    }

    @Override
    public void updateBookGenre(String bookName, String bookAuthor, String bookNewGenre) {
        cs.printMsg(ms.getMessage("bs.msg.ubg.att", null, LocaleContextHolder.getLocale()));

        try {
            Genre newGenre = new Genre(bookNewGenre);
            Author author = authorDao.getByName(bookAuthor);
            if(!genreService.isGenreExists(newGenre)) {
                genreDao.insert(newGenre);
            }
            newGenre = genreDao.getByName(bookNewGenre);

            Book book = bookDao.getByName(bookName, author.getId());
            bookDao.updateBookGenre(book.getId(), newGenre.getId());
            cs.printMsg(ms.getMessage("bs.msg.ubg.done", null, LocaleContextHolder.getLocale()));

        } catch (Exception e) {
            cs.printMsg(ms.getMessage("bs.err.uba", null, LocaleContextHolder.getLocale()));
        }
    }
}
