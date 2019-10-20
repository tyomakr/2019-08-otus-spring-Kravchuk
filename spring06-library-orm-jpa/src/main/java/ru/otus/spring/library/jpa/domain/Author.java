package ru.otus.spring.library.jpa.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "authors")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="authorid", unique = true, nullable = false)
    private long authorId;
    @Column(name ="authorname")
    private String authorName;


    public Author(String authorName) {
        this.authorName = authorName;
    }
}
