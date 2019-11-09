package ru.otus.spring.library.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "authors")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Author {

    @Id
    private String id;
    @Field(name = "authorName")
    private String authorName;


    public Author(String authorName) {
        this.authorName = authorName;
    }
}
