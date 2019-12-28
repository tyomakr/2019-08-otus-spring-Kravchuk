package ru.otus.spring.migration.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.migration.domain.mongodb.AuthorMongo;
import ru.otus.spring.migration.domain.mongodb.BookMongo;
import ru.otus.spring.migration.domain.mongodb.CommentMongo;
import ru.otus.spring.migration.domain.mongodb.GenreMongo;
import ru.otus.spring.migration.domain.sql.AuthorSql;
import ru.otus.spring.migration.domain.sql.BookSql;
import ru.otus.spring.migration.domain.sql.CommentSql;
import ru.otus.spring.migration.domain.sql.GenreSql;
import ru.otus.spring.migration.repository.sql.BookSqlRepository;
import ru.otus.spring.migration.service.ConvertService;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ConvertServiceImpl implements ConvertService {

    private final BookSqlRepository bookSqlRepository;

    @Override
    public AuthorMongo authorToMongo(AuthorSql authorSql) {
        return new AuthorMongo(String.valueOf(authorSql.getAuthorId()), authorSql.getAuthorName());
    }

    @Override
    public GenreMongo genreToMongo(GenreSql genreSql) {
        return new GenreMongo(String.valueOf(genreSql.getGenreId()), genreSql.getGenreName());
    }

    @Override
    public BookMongo bookToMongo(BookSql bookSql) {

        List<AuthorMongo> al = new ArrayList<>();
        List<GenreMongo> gl = new ArrayList<>();
        for (AuthorSql author : bookSql.getAuthorsList()) {
            al.add(authorToMongo(author));
        }
        for(GenreSql genre : bookSql.getGenresList()) {
            gl.add(genreToMongo(genre));
        }

        return new BookMongo(String.valueOf(bookSql.getBookId()), bookSql.getBookName(), al, gl);
    }

    @Override
    public CommentMongo commentToMongo(CommentSql commentSql) {
        return new CommentMongo(String.valueOf(commentSql.getCommentId()), commentSql.getCommentText(),
                bookToMongo(bookSqlRepository.getOne(commentSql.getBook().getBookId())));
    }


}
