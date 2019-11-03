package ru.otus.spring.library.model;

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
    @DBRef
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
}
