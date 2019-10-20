package ru.otus.spring.library.jpa.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "comments")
@Data
@NoArgsConstructor
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="commentid", unique = true, nullable = false)
    private long commentId;
    @Column(name ="commenttext")
    private String commentText;

    @ManyToOne(fetch = FetchType.LAZY, optional = false, cascade = CascadeType.ALL)
    @JoinTable(name = "bookscomments", joinColumns = @JoinColumn(name = "bookid"), inverseJoinColumns = @JoinColumn(name = "commentid"))
    private Book book;


    public Comment(String commentText, Book book) {
        this.commentText = commentText;
        this.book = book;
    }
}
