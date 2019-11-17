package ru.otus.spring.library.webmvc.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.library.webmvc.domain.Author;
import ru.otus.spring.library.webmvc.domain.Book;
import ru.otus.spring.library.webmvc.domain.Genre;
import ru.otus.spring.library.webmvc.rest.dto.BookDto;
import ru.otus.spring.library.webmvc.service.AuthorService;
import ru.otus.spring.library.webmvc.service.GenreService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookMapperImpl implements BookMapper{

    private final AuthorService authorService;
    private final GenreService genreService;


    @Override
    public Book dtoToBook(BookDto bookDto) {
        return new Book(bookDto.getTitle(), Collections.singletonList(authorService.findOrCreateAuthor(bookDto.getAuthors())), Collections.singletonList(genreService.findOrCreateGenre(bookDto.getGenres())));
    }

    @Override
    public BookDto bookDtoToDomain(Book book) {
        return new BookDto(book.getId(), book.getTitle(), authorsToString(book.getAuthors()), genresToString(book.getGenres()));
    }

    @Override
    public List<BookDto> domainToDto(List<Book> books) {
        List<BookDto> booksDto = new ArrayList<>();
        for (Book book : books) {
            booksDto.add(new BookDto(book.getId(), book.getTitle(), authorsToString(book.getAuthors()), genresToString(book.getGenres())));
        }
        return booksDto;
    }


    private String authorsToString(List<Author> authors) {
        return authors.stream().map(Author::getAuthorName).collect(Collectors.joining(" / "));
    }

    private String genresToString(List<Genre> genres) {
        return genres.stream().map(Genre::getGenreTitle).collect(Collectors.joining(" / "));
    }
}