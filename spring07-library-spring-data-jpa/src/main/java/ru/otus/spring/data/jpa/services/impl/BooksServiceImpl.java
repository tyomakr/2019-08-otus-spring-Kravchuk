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
        List<Book> bookList = booksRepo.findAll();
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
        booksRepo.save(book);
    }

    @Override
    @Transactional
    public void addAuthorToBook(Long bookId, String authorName){

        Author author = as.getAuthorByName(authorName);
        Optional<Book> book = booksRepo.findById(bookId);
        if(book.isPresent() ) {
            book.get().getAuthorsList().add(author);
            booksRepo.save(book.get());
        }
    }

    @Override
    @Transactional
    public void addGenreToBook(Long bookId, String genreName) {

        Genre genre = gs.getGenreByName(genreName);
        Optional<Book> book = booksRepo.findById(bookId);
        if (book.isPresent()) {
            book.get().getGenresList().add(genre);
            booksRepo.save(book.get());
        }
    }


    @Override
    public void updateBookName(Long bookId, String newBookName) {
        Optional<Book> book = booksRepo.findById(bookId);
        if (book.isPresent()) {
            book.get().setBookName(newBookName);
            booksRepo.save(book.get());
        }
    }

    @Override
    @Transactional
    public void updateBookAuthor(Long bookId, String originalBookAuthor, String changedBookAuthor) {

        Optional<Book> book = booksRepo.findById(bookId);

        if (book.isPresent()) {
            Author chAuthor = as.getAuthorByName(changedBookAuthor);
            boolean isChanged = false;
            List<Author> al = book.get().getAuthorsList();

            for (Author a : al) {
                if (a.getAuthorName().equalsIgnoreCase(originalBookAuthor)) {
                    ioService.printMsg("bs.msg.ca.change");
                    al.remove(a);
                    al.add(chAuthor);
                    isChanged = true;
                }
            }
            if (isChanged) {
                book.get().setAuthorsList(al);
                booksRepo.save(book.get());
                ioService.printMsg("bs.msg.ca.changed");
            } else {
                ioService.printMsg("bs.msg.not.changed");
            }
        }
    }

    @Override
    @Transactional
    public void updateBookGenre(Long bookId, String originalBookGenre, String changedBookGenre) {

        Optional<Book> book = booksRepo.findById(bookId);

        if (book.isPresent()) {
            Genre chGenre = gs.getGenreByName(changedBookGenre);
            boolean isChanged = false;
            List<Genre> gl = book.get().getGenresList();

            for (Genre g : gl) {
                if (g.getGenreName().equalsIgnoreCase(originalBookGenre)) {
                    ioService.printMsg("bs.msg.cg.change");
                    gl.remove(g);
                    gl.add(chGenre);
                    isChanged = true;
                }
            }
            if (isChanged) {
                book.get().setGenresList(gl);
                booksRepo.save(book.get());
                ioService.printMsg("bs.msg.cg.changed");
            } else {
                ioService.printMsg("bs.msg.not.changed");
            }
        }
    }

    @Override
    @Transactional
    public void deleteBook(Long id) {
        Optional<Book> book = booksRepo.findById(id);
        book.ifPresent(booksRepo::delete);
    }


    @Override
    @Transactional
    public void removeAuthorToBook(Long bookId, String authorName) {

        Optional<Book> book = booksRepo.findById(bookId);

        if (book.isPresent()) {
            boolean isChanged = false;

            if (book.get().getAuthorsList().size() > 1) {
                List<Author> al = book.get().getAuthorsList();
                for (Author author : al) {
                    if (author.getAuthorName().equalsIgnoreCase(authorName)) {
                        al.remove(author);
                        isChanged = true;
                    }
                }
            }
            if (isChanged) {
                booksRepo.save(book.get());
            }
            if (!isChanged) {
                ioService.printMsg("bs.msg.not.changed");
            }
        }
    }

    @Override
    @Transactional
    public void removeGenreToBook(Long bookId, String genreName) {

        Optional<Book> book = booksRepo.findById(bookId);

        if (book.isPresent()) {
            boolean isChanged = false;

            if (book.get().getGenresList().size() > 1) {
                List<Genre> gl = book.get().getGenresList();
                for (Genre genre : gl) {
                    if (genre.getGenreName().equalsIgnoreCase(genreName)) {
                        gl.remove(genre);
                        isChanged = true;
                    }
                }
            }
            if (isChanged) {
                booksRepo.save(book.get());
            }
            if (!isChanged) {
                ioService.printMsg("bs.msg.not.changed");
            }

            booksRepo.save(book.get());
        }
    }
}
