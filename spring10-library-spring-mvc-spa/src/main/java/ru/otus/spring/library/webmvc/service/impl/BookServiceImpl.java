package ru.otus.spring.library.webmvc.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.library.webmvc.domain.Author;
import ru.otus.spring.library.webmvc.domain.Book;
import ru.otus.spring.library.webmvc.domain.Genre;
import ru.otus.spring.library.webmvc.repository.BookRepository;
import ru.otus.spring.library.webmvc.service.AuthorService;
import ru.otus.spring.library.webmvc.service.BookService;
import ru.otus.spring.library.webmvc.service.GenreService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final AuthorService authorService;
    private final GenreService genreService;

    @Override
    public Optional<Book> findById(String bookId) {
        return bookRepository.findById(bookId);
    }

    @Override
    public List<Book> findAll() {
        return bookRepository.findAll();
    }


    @Override
    public Book insertBook(String bookTitle, String bookAuthor, String bookGenre) {
        Author author = authorService.findOrCreateAuthor(bookAuthor);
        Genre genre = genreService.findOrCreateGenre(bookGenre);

        bookRepository.save(new Book(bookTitle, author, genre));
        return null;
    }


    @Override
    public Book updateBook(Book book) {

        List<Author> al = new ArrayList<>();
        List<Genre> gl = new ArrayList<>();

        for(Author author : book.getAuthors()) {
            author = authorService.findOrCreateAuthor(author.getAuthorName());
            al.add(author);
        }
        for(Genre genre : book.getGenres()) {
            genre = genreService.findOrCreateGenre(genre.getGenreTitle());
            gl.add(genre);
        }

        return bookRepository.save(new Book(book.getId(), book.getTitle(), al, gl));
    }


    @Override
    public void addBookAuthor(String bookId, String bookAuthor) {

        Optional<Book> optionalBook = bookRepository.findById(bookId);
        optionalBook.ifPresent(book -> {
            Optional<Author> optionalAuthor = authorService.findById(bookAuthor);
            optionalAuthor.ifPresent(author -> {
                List<Author> al = book.getAuthors();
                al.add(author);
                book.setAuthors(al);
                bookRepository.save(book);
            });
        });
    }

    @Override
    public void removeBookAuthor(String bookId, String authorId) {

        Optional<Book> optionalBook = bookRepository.findById(bookId);
        optionalBook.filter(book -> book.getAuthors().size() > 1)
                .ifPresent(book -> {
                    List<Author> al = new ArrayList<>(optionalBook.get().getAuthors());
                    al.removeIf(author -> author.getId().equals(authorId));
                    book.setAuthors(al);
                    bookRepository.save(book);
                });
    }

    @Override
    public void addBookGenre(String bookId, String bookGenre) {

        Optional<Book> optionalBook = bookRepository.findById(bookId);
        optionalBook.ifPresent(book -> {
            Optional<Genre> optionalGenre = genreService.findById(bookGenre);
            optionalGenre.ifPresent(genre -> {
                List<Genre> gl = book.getGenres();
                gl.add(genre);
                book.setGenres(gl);
                bookRepository.save(book);
            });
        });
    }

    @Override
    public void removeBookGenre(String bookId, String bookGenre) {

        Optional<Book> optionalBook = bookRepository.findById(bookId);
        optionalBook
                .filter(book -> book.getGenres().size() > 1)
                .ifPresent(book -> {
                    List<Genre> gl = new ArrayList<>(optionalBook.get().getGenres());
                    gl.removeIf(genre -> genre.getId().equals(bookGenre));
                    book.setGenres(gl);
                    bookRepository.save(book);
                });
    }

    @Override
    public void removeBook(String bookId) {
        Optional<Book> bookOptional = bookRepository.findById(bookId);
        bookOptional.ifPresent(bookRepository::delete);
    }
}
