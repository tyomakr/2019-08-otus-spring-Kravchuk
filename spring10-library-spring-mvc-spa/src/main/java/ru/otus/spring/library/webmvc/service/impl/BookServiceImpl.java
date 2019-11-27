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

        return bookRepository.save(new Book(bookTitle, author, genre));
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
    public void removeBook(String bookId) {
        Optional<Book> bookOptional = bookRepository.findById(bookId);
        bookOptional.ifPresent(bookRepository::delete);
    }
}
