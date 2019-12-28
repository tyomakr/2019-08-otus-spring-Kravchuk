package ru.otus.spring.migration.domain.mongodb;

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
public class GenreMongo {

    @Id
    private String id;
    @Field(name = "genreTitle")
    private String genreTitle;


    public GenreMongo(String genreTitle) {
        this.genreTitle = genreTitle;
    }
}