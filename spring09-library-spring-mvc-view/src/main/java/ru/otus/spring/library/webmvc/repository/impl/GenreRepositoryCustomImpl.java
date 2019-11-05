package ru.otus.spring.library.webmvc.repository.impl;

import com.mongodb.client.result.UpdateResult;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import ru.otus.spring.library.webmvc.domain.Book;
import ru.otus.spring.library.webmvc.repository.GenreRepositoryCustom;

import static org.springframework.data.mongodb.core.query.Criteria.where;

@RequiredArgsConstructor
public class GenreRepositoryCustomImpl implements GenreRepositoryCustom {

    private final MongoTemplate mongoTemplate;

    @Override
    public UpdateResult updateAllGenresInBooks(String oldGenreTitle, String newGenreTitle) {

        return mongoTemplate.updateMulti(
                new Query(where("genres.genreTitle").is(oldGenreTitle)),
                new Update().set("genres.$.genreTitle", newGenreTitle), Book.class);
    }
}
