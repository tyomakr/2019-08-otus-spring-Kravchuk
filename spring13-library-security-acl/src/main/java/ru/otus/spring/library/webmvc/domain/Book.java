package ru.otus.spring.library.webmvc.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Document(collection = "books")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Book {

    @Id
    private String id;
    @Field(name = "title")
    private String title;
    @DBRef
    @Field(name = "authors")
    private List<Author> authors;
    @Field(name = "genres")
    private List<Genre> genres;


    public Book(String title) {
        this.title = title;
        authors = new ArrayList<>();
        genres = new ArrayList<>();
    }

    public Book(String bookName, List<Author> authors, List<Genre> genres) {
        this.title = bookName;
        this.authors = authors;
        this.genres = genres;
    }


    public Book(String title, Author author, Genre genre) {
        this.title = title;
        authors = new ArrayList<>(Collections.singletonList(author));
        genres = new ArrayList<>(Collections.singletonList(genre));
    }

    public String authorsToString() {
        return authors.stream().map(Author::getAuthorName).collect(Collectors.joining(" / "));
    }

    public String genresToString() {
        return genres.stream().map(Genre::getGenreTitle).collect(Collectors.joining(" / "));
    }


}