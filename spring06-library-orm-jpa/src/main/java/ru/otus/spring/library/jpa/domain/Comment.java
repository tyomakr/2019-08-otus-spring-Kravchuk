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
    private long authorId;
    @Column(name ="commenttext")
    private String authorName;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "bookscomments", joinColumns = @JoinColumn(name = "bookid"), inverseJoinColumns = @JoinColumn(name = "commentid"))
    private Book book;

}
