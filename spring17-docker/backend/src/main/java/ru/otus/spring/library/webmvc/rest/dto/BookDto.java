package ru.otus.spring.library.webmvc.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.otus.spring.library.webmvc.domain.Author;
import ru.otus.spring.library.webmvc.domain.Book;
import ru.otus.spring.library.webmvc.domain.Genre;

import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
public class BookDto {

    private String id;
    private String title;
    private String authors;
    private String genres;

    public static BookDto toDto(Book book) {
        return new BookDto(book.getId(), book.getTitle(), authorsToString(book.getAuthors()), genresToString(book.getGenres()));
    }


    private static String authorsToString(List<Author> authors) {
        return authors.stream().map(Author::getAuthorName).collect(Collectors.joining(" / "));
    }

    private static String genresToString(List<Genre> genres) {
        return genres.stream().map(Genre::getGenreTitle).collect(Collectors.joining(" / "));
    }
}
