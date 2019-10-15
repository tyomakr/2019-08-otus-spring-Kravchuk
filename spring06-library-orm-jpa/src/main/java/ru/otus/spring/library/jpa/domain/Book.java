package ru.otus.spring.library.jpa.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "books")
@Data
@NoArgsConstructor
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="bookid", unique = true, nullable = false)
    private long bookId;

    @Column(name ="bookname")
    private String bookName;


    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "booksauthors", joinColumns = @JoinColumn(name = "bookid"), inverseJoinColumns = @JoinColumn(name = "authorid"))
    private List<Author> authorsList;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "booksgenres", joinColumns = @JoinColumn(name = "bookid"), inverseJoinColumns = @JoinColumn(name = "genreid"))
    private List<Genre> genresList;


    public Book(String bookName) {
        this.bookName = bookName;
    }
}
