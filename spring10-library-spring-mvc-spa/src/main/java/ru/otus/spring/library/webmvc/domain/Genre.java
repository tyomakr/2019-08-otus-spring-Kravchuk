package ru.otus.spring.library.webmvc.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Genre {

    @Id
    private String id;
    @Field(name = "genreTitle")
    private String genreTitle;


    public Genre(String genreTitle) {
        this.genreTitle = genreTitle;
    }
}