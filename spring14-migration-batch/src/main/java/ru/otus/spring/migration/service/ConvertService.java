package ru.otus.spring.migration.service;

import ru.otus.spring.migration.domain.mongodb.AuthorMongo;
import ru.otus.spring.migration.domain.mongodb.BookMongo;
import ru.otus.spring.migration.domain.mongodb.CommentMongo;
import ru.otus.spring.migration.domain.mongodb.GenreMongo;
import ru.otus.spring.migration.domain.sql.AuthorSql;
import ru.otus.spring.migration.domain.sql.BookSql;
import ru.otus.spring.migration.domain.sql.CommentSql;
import ru.otus.spring.migration.domain.sql.GenreSql;

public interface ConvertService {

    AuthorMongo authorToMongo(AuthorSql authorSql);

    GenreMongo genreToMongo(GenreSql genreSql);

    BookMongo bookToMongo(BookSql bookSql);

    CommentMongo commentToMongo(CommentSql commentSql);

}
