package ru.otus.spring.library.jpa.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "comments")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="commentid", unique = true, nullable = false)
    private long commentId;


    @Column(name ="commenttext")
    private String commentText;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "bookid")
    private Book book;


    public Comment(String commentText, Book book) {
        this.commentText = commentText;
        this.book = book;
    }
}
