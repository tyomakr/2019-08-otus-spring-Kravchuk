package ru.otus.spring.library.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.library.model.Author;
import ru.otus.spring.library.model.Book;
import ru.otus.spring.library.model.Genre;
import ru.otus.spring.library.repository.AuthorRepository;
import ru.otus.spring.library.repository.BookRepository;
import ru.otus.spring.library.repository.GenreRepository;
import ru.otus.spring.library.services.BookService;
import ru.otus.spring.library.services.IOService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final GenreRepository genreRepository;
    private final IOService ioService;

    @Override
    public void findAll() {
        List<Book> bookList = bookRepository.findAll();

        String tableFormatter = "%-26s %-50s %-60s %-60s %n";
        ioService.printItemsList(tableFormatter, ioService.getMsg("bs.head.b.id"), ioService.getMsg("bs.head.b.name"),
                ioService.getMsg("bs.head.b.author"), ioService.getMsg("bs.head.b.genre"));

        for (Book book : bookList) {
            ioService.printItemsList(tableFormatter, book.getId(), book.getTitle(),
                    book.getAuthors().stream().map(Author::getAuthorName).collect(Collectors.joining(" / ")),
                    book.getGenres().stream().map(Genre::getGenreTitle).collect(Collectors.joining(" / ")));
        }
    }

    @Override
    public void insertBook(String bookTitle, String bookAuthor, String bookGenre) {

        Author author = findOrCreateAuthor(bookAuthor);
        Genre genre = findOrCreateGenre(bookGenre);

        bookRepository.save(new Book(bookTitle, author, genre));
    }

    @Override
    public void updateBookName(String bookId, String newBookTitle) {

        Optional<Book> optionalBook = bookRepository.findById(bookId);
        ioService.printMsg(optionalBook.map(book ->{
            book.setTitle(newBookTitle);
            bookRepository.save(book);
            return "bs.bn.updated";
        }).orElse("bs.err.b.id.not.exist"));
    }

    @Override
    public void updateBookAuthor(String bookId, String oldBookAuthor, String newBookAuthor) {

        Optional<Book> optionalBook = bookRepository.findById(bookId);
        ioService.printMsg(optionalBook.map(book -> {
            Author changedAuthor = findOrCreateAuthor(newBookAuthor);
            List<Author> authors = book.getAuthors();
            authors.removeIf(author -> author.getAuthorName().equalsIgnoreCase(oldBookAuthor));
            authors.add(changedAuthor);
            bookRepository.save(optionalBook.get());
            return "bs.au.updated";
        }).orElse("bs.err.b.id.not.exist"));
    }

    @Override
    public void updateBookGenre(String bookId, String oldBookGenre, String newBookGenre) {

        Optional<Book> optionalBook = bookRepository.findById(bookId);
        ioService.printMsg(optionalBook.map(book -> {
            Genre changedGenre = findOrCreateGenre(newBookGenre);
            List<Genre> genres = book.getGenres();
            genres.removeIf(genre -> genre.getGenreTitle().equalsIgnoreCase(oldBookGenre));
            genres.add(changedGenre);
            bookRepository.save(optionalBook.get());
            return "bs.gu.updated";
        }).orElse("bs.err.b.id.not.exist"));
    }

    @Override
    public void addBookAuthor(String bookId, String bookAuthor) {

        Optional<Book> optionalBook = bookRepository.findById(bookId);
        ioService.printMsg(optionalBook.map(book -> {
            Author author = findOrCreateAuthor(bookAuthor);
            List<Author> al = book.getAuthors();
            al.add(author);
            book.setAuthors(al);
            bookRepository.save(book);
            return "bs.au.added";
        }).orElse("bs.err.b.id.not.exist"));
    }

    @Override
    public void removeBookAuthor(String bookId, String bookAuthor) {

        Optional<Book> optionalBook = bookRepository.findById(bookId);

        ioService.printMsg(optionalBook
                .filter(book -> book.getAuthors().size() > 1)
                .map(book -> {
                    List<Author> al = new ArrayList<>(optionalBook.get().getAuthors());
                    al.removeIf(author -> author.getAuthorName().equalsIgnoreCase(bookAuthor));
                    book.setAuthors(al);
                    bookRepository.save(book);
                    return "bs.ar.removed";
                }).orElse("bs.err.b.rba"));
    }

    @Override
    public void addBookGenre(String bookId, String bookGenre) {

        Optional<Book> optionalBook = bookRepository.findById(bookId);
        ioService.printMsg(optionalBook.map(book -> {
            Genre genre = findOrCreateGenre(bookGenre);
            List<Genre> gl = book.getGenres();
            gl.add(genre);
            book.setGenres(gl);
            bookRepository.save(book);
            return "bs.gu.added";
        }).orElse("bs.err.b.id.not.exist"));
    }


    @Override
    public void removeBookGenre(String bookId, String bookGenre) {

        Optional<Book> optionalBook = bookRepository.findById(bookId);
        ioService.printMsg(optionalBook
                .filter(book -> book.getGenres().size() > 1)
                .map(book -> {
                    List<Genre> gl = new ArrayList<>(optionalBook.get().getGenres());
                    gl.removeIf(genre -> genre.getGenreTitle().equalsIgnoreCase(bookGenre));
                    book.setGenres(gl);
                    bookRepository.save(book);
                    return "bs.gr.removed";
        }).orElse("bs.err.b.rbg"));
    }


    @Override
    public void removeBook(String bookId) {
        Optional<Book> bookOptional = bookRepository.findById(bookId);
        bookOptional.ifPresent(bookRepository::delete);
    }


    private Author findOrCreateAuthor(String bookAuthor) {
        Optional<Author> authorOptional = authorRepository.findAuthorByAuthorName(bookAuthor);
        return authorRepository.save(authorOptional.orElseGet(() -> new Author(bookAuthor)));
    }

    private Genre findOrCreateGenre(String bookGenre) {
        Optional<Genre> genreOptional = genreRepository.findGenreByGenreTitle(bookGenre);
        return genreRepository.save(genreOptional.orElseGet(() -> new Genre(bookGenre)));
    }

}
