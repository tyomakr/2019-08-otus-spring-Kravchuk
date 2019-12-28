package ru.otus.spring.migration.domain.mongodb;

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
public class BookMongo {

    @Id
    private String id;
    @Field(name = "title")
    private String title;
    @DBRef
    @Field(name = "authors")
    private List<AuthorMongo> authors;
    @Field(name = "genres")
    private List<GenreMongo> genres;


    public BookMongo(String title) {
        this.title = title;
        authors = new ArrayList<>();
        genres = new ArrayList<>();
    }

    public BookMongo(String bookName, List<AuthorMongo> authors, List<GenreMongo> genres) {
        this.title = bookName;
        this.authors = authors;
        this.genres = genres;
    }


    public BookMongo(String title, AuthorMongo author, GenreMongo genre) {
        this.title = title;
        authors = new ArrayList<>(Collections.singletonList(author));
        genres = new ArrayList<>(Collections.singletonList(genre));
    }


}