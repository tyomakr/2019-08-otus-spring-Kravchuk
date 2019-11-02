package ru.otus.spring.library.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Comment {

    @Id
    private String id;
    @Field(name = "commentText")
    private String commentText;
    @DBRef
    @Field(name = "book")
    private Book book;


    public Comment(String commentText, Book book) {
        this.commentText = commentText;
        this.book = book;
    }
}
