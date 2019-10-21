package ru.otus.spring.data.jpa.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.data.jpa.domain.Author;
import ru.otus.spring.data.jpa.domain.Book;
import ru.otus.spring.data.jpa.domain.Genre;
import ru.otus.spring.data.jpa.repository.BooksRepository;
import ru.otus.spring.data.jpa.services.AuthorsService;
import ru.otus.spring.data.jpa.services.BooksService;
import ru.otus.spring.data.jpa.services.GenresService;
import ru.otus.spring.data.jpa.services.IOService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BooksServiceImpl implements BooksService {

    private final BooksRepository booksRepo;
    private final AuthorsService as;
    private final GenresService gs;
    private final IOService ioService;

    @Override
    @Transactional
    public void findAllBooks() {
        List<Book> bookList = booksRepo.findAllBooks();
        ioService.printMsg("bs.msg.list");

        String tableFormatter = "%-4s %-50s %-50s %-50s %n";

        ioService.printItemsList(tableFormatter, ioService.getMsg("bs.msg.l.h0"),
                ioService.getMsg("bs.msg.l.h1"), ioService.getMsg("bs.msg.l.h2"),
                ioService.getMsg("bs.msg.l.h3"));
        for (Book book : bookList) {
            ioService.printItemsList(tableFormatter, book.getBookId(), book.getBookName(),
                    book.getAuthorsList().stream().map(Author::getAuthorName).collect(Collectors.toList()),
                    book.getGenresList().stream().map(Genre::getGenreName).collect(Collectors.toList()));
        }
    }

    @Override
    @Transactional
    public void addBook(String bookName, String authorName, String genreName) {

        Author author = as.getAuthorByName(authorName);
        Genre genre = gs.getGenreByName(genreName);

        Book book = new Book(bookName, author, genre);
        booksRepo.saveBook(book);
    }

    @Override
    @Transactional
    public void addAuthorToBook(Long bookId, String authorName){

        Author author = as.getAuthorByName(authorName);
        Book book = booksRepo.findBookById(bookId);
        book.getAuthorsList().add(author);
        booksRepo.saveBook(book);
    }

    @Override
    @Transactional
    public void addGenreToBook(Long bookId, String genreName) {

        Genre genre = gs.getGenreByName(genreName);
        Book book = booksRepo.findBookById(bookId);
        book.getGenresList().add(genre);
        booksRepo.saveBook(book);
    }


    @Override
    public void updateBookName(Long bookId, String newBookName) {
        try {
            Book book = booksRepo.findBookById(bookId);
            book.setBookName(newBookName);
            booksRepo.saveBook(book);
        } catch (Exception e) {
            ioService.printMsg("bs.err.not.found");
        }
    }

    @Override
    @Transactional
    public void updateBookAuthor(Long bookId, String originalBookAuthor, String changedBookAuthor) {

        Book book = booksRepo.findBookById(bookId);
        Author chAuthor = as.getAuthorByName(changedBookAuthor);
        boolean isChanged = false;
        List<Author> al = book.getAuthorsList();

        for (Author a : al) {
            if (a.getAuthorName().equalsIgnoreCase(originalBookAuthor)) {
                ioService.printMsg("bs.msg.ca.change");
                al.remove(a);
                al.add(chAuthor);
                isChanged = true;
            }
        }
        if (isChanged) {
            book.setAuthorsList(al);
            booksRepo.saveBook(book);
            ioService.printMsg("bs.msg.ca.changed");
        } else {
            ioService.printMsg("bs.msg.not.changed");
        }
    }

    @Override
    @Transactional
    public void updateBookGenre(Long bookId, String originalBookGenre, String changedBookGenre) {

        Book book = booksRepo.findBookById(bookId);
        Genre chGenre = gs.getGenreByName(changedBookGenre);
        boolean isChanged = false;
        List<Genre> gl = book.getGenresList();

        for (Genre g : gl) {
            if (g.getGenreName().equalsIgnoreCase(originalBookGenre)) {
                ioService.printMsg("bs.msg.cg.change");
                gl.remove(g);
                gl.add(chGenre);
                isChanged = true;
            }
        }
        if (isChanged) {
            book.setGenresList(gl);
            booksRepo.saveBook(book);
            ioService.printMsg("bs.msg.cg.changed");
        } else {
            ioService.printMsg("bs.msg.not.changed");
        }
    }

    @Override
    @Transactional
    public void deleteBook(Long id) {
        Optional<Book> book = Optional.ofNullable(booksRepo.findBookById(id));
        book.ifPresent(booksRepo::deleteBook);
    }


    @Override
    @Transactional
    public void removeAuthorToBook(Long bookId, String authorName) {
        try {
            Book book = booksRepo.findBookById(bookId);
            boolean isChanged = false;

            if (book.getAuthorsList().size() > 1) {
                List<Author>al = book.getAuthorsList();
                for(Author author : al) {
                    if (author.getAuthorName().equalsIgnoreCase(authorName)) {
                        al.remove(author);
                        isChanged = true;
                    }
                }
            }
            if (isChanged) {
                booksRepo.saveBook(book);
            }
            if (!isChanged) {
                ioService.printMsg("bs.msg.not.changed");
            }

        } catch (Exception e) {
            ioService.printMsg("bs.err.not.found");
        }
    }

    @Override
    @Transactional
    public void removeGenreToBook(Long bookId, String genreName) {
        try {
            Book book = booksRepo.findBookById(bookId);
            boolean isChanged = false;

            if (book.getGenresList().size() > 1) {
                List<Genre>gl = book.getGenresList();
                for(Genre genre : gl) {
                    if (genre.getGenreName().equalsIgnoreCase(genreName)) {
                        gl.remove(genre);
                        isChanged = true;
                    }
                }
            }
            if (isChanged) {
                booksRepo.saveBook(book);
            }
            if (!isChanged) {
                ioService.printMsg("bs.msg.not.changed");
            }

            booksRepo.saveBook(book);
        } catch (Exception e) {
            ioService.printMsg("bs.err.not.found");
        }
    }

}
