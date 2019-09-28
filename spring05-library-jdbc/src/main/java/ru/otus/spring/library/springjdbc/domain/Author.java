package ru.otus.spring.library.springjdbc.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Author {

    private int id;
    private String authorName;


    public Author(String authorName) {
        this.authorName = authorName;
    }
}
