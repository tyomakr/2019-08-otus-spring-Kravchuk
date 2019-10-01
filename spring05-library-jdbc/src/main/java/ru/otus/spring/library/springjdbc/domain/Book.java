package ru.otus.spring.library.springjdbc.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Book {

    private long id;
    private String bookName;
    private Author author;
    private Genre genre;


    public Book(String bookName, Author author, Genre genre) {
        this.bookName = bookName;
        this.author = author;
        this.genre = genre;
    }
}
