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

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final AuthorService authorService;
    private final GenreService genreService;

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
        Author author = authorService.findOrCreateAuthor(bookAuthor);
        Genre genre = genreService.findOrCreateGenre(bookGenre);

        bookRepository.save(new Book(bookTitle, author, genre));
    }

    @Override
    public void updateBook(Book eBook) {

        List<Author> al = new ArrayList<>();
        for(Author a : eBook.getAuthors()) {
            al.add(authorService.findOrCreateAuthor(a.getAuthorName()));
        }
        List<Genre> gl = new ArrayList<>();
        for (Genre g : eBook.getGenres()) {
            gl.add(genreService.findOrCreateGenre(g.getGenreTitle()));
        }
        bookRepository.save(new Book(eBook.getId(), eBook.getTitle(), al, gl));
    }

}
