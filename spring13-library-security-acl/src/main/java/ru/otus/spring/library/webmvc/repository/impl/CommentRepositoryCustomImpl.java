package ru.otus.spring.library.webmvc.repository.impl;

import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import ru.otus.spring.library.webmvc.domain.Comment;
import ru.otus.spring.library.webmvc.repository.CommentRepositoryCustom;

@RequiredArgsConstructor
public class CommentRepositoryCustomImpl implements CommentRepositoryCustom {

    private final MongoTemplate mongoTemplate;

    @Override
    public void removeAllCommentsFromBookByBookId(String bookId) {
        Query query = Query.query(Criteria.where("book.$id").is(new ObjectId(bookId)));
        mongoTemplate.remove(query, Comment.class);
    }
}