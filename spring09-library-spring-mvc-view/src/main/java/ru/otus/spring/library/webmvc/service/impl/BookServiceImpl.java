package ru.otus.spring.library.webmvc.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.library.webmvc.domain.Author;
import ru.otus.spring.library.webmvc.domain.Book;
import ru.otus.spring.library.webmvc.domain.Genre;
import ru.otus.spring.library.webmvc.repository.AuthorRepository;
import ru.otus.spring.library.webmvc.repository.BookRepository;
import ru.otus.spring.library.webmvc.repository.GenreRepository;
import ru.otus.spring.library.webmvc.service.BookService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final GenreRepository genreRepository;

    @Override
    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    @Override
    public Book findById(String bookId) {
        return bookRepository.findById(bookId).get();
    }

    @Override
    public void insertBook(String bookTitle, String bookAuthor, String bookGenre) {
        Author author = findOrCreateAuthor(bookAuthor);
        Genre genre = findOrCreateGenre(bookGenre);

        bookRepository.save(new Book(bookTitle, author, genre));
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
